package com.raspisanie.mai.ui.main.settings
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.BuildConfig
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.raspisanie.mai.extesions.getIsDayTheme
import com.raspisanie.mai.extesions.saveIsDayTheme
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item_info.view.*
import timber.log.Timber

class SettingsFragment : BaseFragment(R.layout.fragment_settings), MvpView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        }

        with(diagramView) {
            setCenterText(resources.getString(R.string.settings_diagram_text))
            setCenterSubText("421 ${resources.getString(R.string.settings_diagram_kb)}")

            setColorText(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    ContextCompat.getColor(context, R.color.colorTextHint)
            )

            setData(
                    arrayListOf(34f, 54f, 10f),
                    arrayListOf(
                            ContextCompat.getColor(context, R.color.colorPrimary),
                            ContextCompat.getColor(context, R.color.colorDiagram2),
                            ContextCompat.getColor(context, R.color.colorDiagram1)
                    )
            )
        }

        tvVersion.text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.VERSION_CODE}"
    }

    fun setView() {
        val views = mutableListOf(
                lChangeGroup, lReloadTimetable, lReloadOther, lEvents,
                lSupport, lNews, lMai
        )
        val titles = mutableListOf(
                resources.getString(R.string.settings_select_group),
                resources.getString(R.string.settings_reload_timetable),
                resources.getString(R.string.settings_reload_other),
                resources.getString(R.string.settings_events_restore),
                resources.getString(R.string.settings_support),
                resources.getString(R.string.settings_news),
                resources.getString(R.string.settings_mai_link)
        )
        val icons = mutableListOf(
                R.drawable.ic_group,
                R.drawable.ic_update,
                R.drawable.ic_update_info,
                R.drawable.ic_events,
                R.drawable.ic_support,
                R.drawable.ic_news,
                R.drawable.ic_site_link
        )

        for (i in views.indices) {
            with(views[i]) {
                Timber.d("title - ${titles[i]}")
                this.tvName.text = titles[i]
                showIcon(this.icIcon, icons[i])
            }
        }

        lNews.tvName.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }

    override fun onBackPressed() {
        presenter.back()
    }
}