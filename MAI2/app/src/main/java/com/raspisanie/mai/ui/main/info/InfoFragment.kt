package com.raspisanie.mai.ui.main.info
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.item_info.*
import kotlinx.android.synthetic.main.item_info.view.*
import timber.log.Timber

class InfoFragment : BaseFragment(R.layout.fragment_info), MvpView {

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lMap.setOnClickListener {
            presenter.openCampusMap()
        }
        lCanteen.setOnClickListener {
            presenter.openCanteens()
        }

        setView()
    }

    private fun setView() {
        val views = mutableListOf(
                lMap, lCanteen, lLibrary,
                lSport, lStudents, lCreative
        )
        val titles = mutableListOf(
                resources.getString(R.string.info_button_map),
                resources.getString(R.string.info_button_canteen),
                resources.getString(R.string.info_button_library),
                resources.getString(R.string.info_button_sport),
                resources.getString(R.string.info_button_students),
                resources.getString(R.string.info_button_creative)
        )
        val icons = mutableListOf(
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
                showIcon(this.icIcon, icons[i])
            }
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}