package com.raspisanie.mai.ui.main.info.campus_map

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.models.human.CanteenHuman
import kotlinx.android.synthetic.main.fragment_canteens.*
import kotlinx.android.synthetic.main.fragment_timetable.include_toolbar
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class CampusMapFragment : BaseFragment(R.layout.fragment_campus_map), MvpView {

    @InjectPresenter
    lateinit var presenter: CampusMapPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onBackPressed() {
        presenter.back()
    }
}