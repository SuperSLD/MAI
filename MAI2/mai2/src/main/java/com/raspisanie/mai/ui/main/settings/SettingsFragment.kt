package com.raspisanie.mai.ui.main.settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.BuildConfig
import com.raspisanie.mai.R
import com.raspisanie.mai.common.extesions.getIsDayTheme
import com.raspisanie.mai.common.extesions.saveIsDayTheme
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.domain.models.ScheduleLocal
import com.raspisanie.mai.extesions.*
import com.yandex.metrica.YandexMetrica
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item_info.view.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopPadding
import timber.log.Timber


class SettingsFragment : BaseFragment(R.layout.fragment_settings), SettingsView {

    companion object {
        const val RESTORE_POSITION = "arg_restore_position"
        const val SAVED_POSITION = "arg_saved_position"
    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    private val colors by lazy { arrayListOf(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram1),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram2),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram3),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram4),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram5),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram6),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram7),
            ContextCompat.getColor(requireContext(), R.color.colorDiagram8)
        )
    }

    private val adapter by lazy { GroupsListAdapter(
            presenter::select,
            presenter::remove,
            context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    ) }

    private val adapterDev by lazy { DevListAdapter(
        this::openLink
    ) }

    private val adapterSchedule by lazy { ScheduleListAdapter(colors) }

    @SuppressLint("SetTextI18n", "HardwareIds")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState?.getBoolean(RESTORE_POSITION) != null
                && savedInstanceState.getBoolean(RESTORE_POSITION)) {
            savedInstanceState.putBoolean(RESTORE_POSITION, false)
            nested.scrollY = savedInstanceState.getInt(SAVED_POSITION)
        }

        val deviceId = Settings.Secure.getString(requireContext().applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        tvDeviceId.text = deviceId

        scrollingContent.addSystemTopPadding()
        scrollingContent.addSystemTopPadding()

        setView()

        swEnabled.isChecked = !context?.getIsDayTheme()!!
        swEnabled.setOnCheckedChangeListener { _, checked ->
            context?.saveIsDayTheme(!checked)
            if (!checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            YandexMetrica.reportEvent("ChangeTheme")

            activity?.recreate()
            savedInstanceState?.putBoolean(RESTORE_POSITION, true)
            savedInstanceState?.putInt(SAVED_POSITION, nested.scrollY)
        }

        btnAdd.setOnClickListener {
            presenter.addGroup()
        }

        tvVersion.text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.VERSION_CODE}"
        with(rvGroups) {
            adapter = this@SettingsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        with(rvSchedules) {
            adapter = this@SettingsFragment.adapterSchedule
            layoutManager = LinearLayoutManager(context)
        }

        with(rvDevs) {
            adapter = this@SettingsFragment.adapterDev
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setView() {
        val views = mutableListOf(
                lSupport, lMai
        )
        val titles = mutableListOf(
                resources.getString(R.string.settings_support),
                resources.getString(R.string.settings_mai_link)
        )
        val icons = mutableListOf(
                R.drawable.ic_support,
                R.drawable.ic_site_link
        )

        for (i in views.indices) {
            with(views[i]) {
                Timber.d("title - ${titles[i]}")
                this.tvName.text = titles[i]
                this.icIcon.setImageDrawable(ContextCompat.getDrawable(context, icons[i]))
            }
        }

        lSupport.setOnClickListener {
            presenter.sendFeedback()
        }

        lMai.setOnClickListener {
            context?.openWebLink("https://mai.ru")
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    fun openLink(link: String) {
        requireContext().openWebLink(link)
    }

    /**
     * Составляем инфу для диаграммы.
     * Достаточно тяжелый процесс.
     *
     * todo нужно проверить все вызываемые методы,
     *      и доделаьб в них кэширование.
     */
    override fun showScheduleInfo(list: MutableList<ScheduleLocal>, groups: MutableList<GroupRealm>) {
        val firstItems = list.firstItems(colors.size)
        with(diagramView) {
            setCenterText(resources.getString(R.string.settings_diagram_text))
            setCenterSubText(resources.getString(R.string.settings_diagram_kb, list.sumBy { it.size }))

            setColorText(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    ContextCompat.getColor(context, R.color.colorTextSecondary)
            )

            setData(
                    firstItems.map { it.size.toFloat() }.toMutableList(),
                    colors
            )
            refresh()
            setOnClickListener {
                YandexMetrica.reportEvent("ClickOnDiagram")
                refresh()
            }
        }
        adapterSchedule.addAll(firstItems, groups)
    }

    override fun showDevList(list: MutableList<DevLocal>) {
        adapterDev.addAll(list)
    }

    override fun toggleLoading(show: Boolean) {
        vLoader.show(show)
        rvDevs.show(!show)
    }

    /**
     * Показываем группу пользователя,
     * если она не выбрана то показываем текст с иконкой.
     */
    override fun showCurrentGroup(group: GroupRealm?) {
        if (group != null) {
            vgGroupEmpty.visibility = View.GONE
            vgGroupInfo.visibility = View.VISIBLE
            tvGroup.text = group.name
            tvLocation.text = group.fac
            tvSpec.text = group.level
            tvYear.text = getString(R.string.select_group_level, group.course)
        } else {
            vgGroupEmpty.visibility = View.VISIBLE
            vgGroupInfo.visibility = View.GONE
        }
    }

    override fun showGroups(groups: MutableList<GroupRealm>) {
        adapter.addAll(groups)
    }

    override fun removeGroup(group: GroupRealm) {
        adapter.removeGroup(group)
    }
}