package com.raspisanie.mai.ui.main.info.campus_map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import pro.midev.supersld.common.base.BaseFragment

class CampusMapFragment : BaseFragment(R.layout.fragment_campus_map), MvpView {

    @InjectPresenter
    lateinit var presenter: CampusMapPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}