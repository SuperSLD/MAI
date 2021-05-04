package com.raspisanie.mai.ui.main.settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.BuildConfig
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.firstItems
import com.raspisanie.mai.extesions.getIsDayTheme
import com.raspisanie.mai.extesions.openWebLink
import com.raspisanie.mai.extesions.saveIsDayTheme
import com.raspisanie.mai.models.local.ScheduleLocal
import com.raspisanie.mai.models.realm.GroupRealm
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item_info.view.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemTopPadding
import timber.log.Timber


class SettingsFragment : BaseFragment(R.layout.fragment_settings), SettingsView {

    companion object {
        const val RESTORE_POSITION = "arg_restore_position"
        const val SAVED_POSITION = "arg_saved_position"
    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    private val colors by lazy { arrayListOf(
            ContextCompat.getColor(context!!, R.color.colorPrimary),
            ContextCompat.getColor(context!!, R.color.colorDiagram1),
            ContextCompat.getColor(context!!, R.color.colorDiagram2),
            ContextCompat.getColor(context!!, R.color.colorDiagram3),
            ContextCompat.getColor(context!!, R.color.colorDiagram4),
            ContextCompat.getColor(context!!, R.color.colorDiagram5),
            ContextCompat.getColor(context!!, R.color.colorDiagram6),
            ContextCompat.getColor(context!!, R.color.colorDiagram7),
            ContextCompat.getColor(context!!, R.color.colorDiagram8)
        )
    }

    private val adapter by lazy { GroupsListAdapter(
            presenter::select,
            presenter::remove,
            context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    ) }

    private val adapterSchedule by lazy { ScheduleListAdapter(colors) }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState?.getBoolean(RESTORE_POSITION) != null
                && savedInstanceState.getBoolean(RESTORE_POSITION)) {
            savedInstanceState.putBoolean(RESTORE_POSITION, false)
            nested.scrollY = savedInstanceState.getInt(SAVED_POSITION)
        }


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
            setOnClickListener { refresh() }
        }
        adapterSchedule.addAll(firstItems, groups)
    }

    override fun showCurrentGroup(group: GroupRealm) {
        tvGroup.text = group.name
        tvLocation.text = group.fac
        tvSpec.text = group.level
        tvYear.text = getString(R.string.select_group_level, group.course)
    }

    override fun showGroups(groups: MutableList<GroupRealm>) {
        adapter.addAll(groups)
    }

    override fun removeGroup(group: GroupRealm) {
        adapter.removeGroup(group)
    }
}