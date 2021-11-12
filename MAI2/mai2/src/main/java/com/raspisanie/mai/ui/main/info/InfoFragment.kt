package com.raspisanie.mai.ui.main.info
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.NotificationsLocal
import com.yandex.metrica.impl.ob.V
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.item_info.view.*
import pro.midev.supersld.common.base.BaseFragment
import timber.log.Timber

class InfoFragment : BaseFragment(R.layout.fragment_info), InfoView {

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lAdv.setOnClickListener {
            presenter.openAdvList()
        }
        lNews.setOnClickListener {
            presenter.openNews()
        }
        lLectors.setOnClickListener {
            presenter.openLectors()
        }
        lMap.setOnClickListener {
            presenter.openCampusMap()
        }
        lCanteen.setOnClickListener {
            presenter.openCanteens()
        }
        lLibrary.setOnClickListener {
            presenter.openLibrary()
        }
        lSport.setOnClickListener {
            presenter.openSport()
        }
        lStudents.setOnClickListener {
            presenter.openStudents()
        }
        lCreative.setOnClickListener {
            presenter.openCreative()
        }

        setView()
    }

    private fun setView() {
        val views = mutableListOf(
                lAdv, lNews, lLectors,
                lMap, lCanteen, lLibrary,
                lSport, lStudents, lCreative
        )
        val titles = mutableListOf(
                resources.getString(R.string.info_button_adv),
                resources.getString(R.string.info_button_news),
                getString(R.string.info_button_lectors),
                resources.getString(R.string.info_button_map),
                resources.getString(R.string.info_button_canteen),
                resources.getString(R.string.info_button_library),
                resources.getString(R.string.info_button_sport),
                resources.getString(R.string.info_button_students),
                resources.getString(R.string.info_button_creative)
        )
        val icons = mutableListOf(
                R.drawable.ic_adv,
                R.drawable.ic_news,
                R.drawable.ic_calendar,
                R.drawable.ic_map,
                R.drawable.ic_canteen,
                R.drawable.ic_library,
                R.drawable.ic_sport,
                R.drawable.ic_students,
                R.drawable.ic_creative
        )

        for (i in views.indices) {
            with(views[i]) {
                Timber.d("title - ${titles[i]}")
                this.tvName.text = titles[i]
                this.icIcon.setImageDrawable(ContextCompat.getDrawable(context, icons[i]))
            }
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showNotifications(notifications: NotificationsLocal) {
        with(lNews) {
            cvNotification.visibility = if (notifications.getNewsCount() > 0) View.VISIBLE else View.GONE
            tvNotification.text = notifications.getNewsCount().toString()
        }
    }
}