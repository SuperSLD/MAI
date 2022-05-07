package com.raspisanie.mai.ui.main.timetble.blacklist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import online.jutter.supersld.common.base.BaseFragment

class BlackListFragment : BaseFragment(R.layout.fragment_black_list), MvpView {

    @InjectPresenter
    lateinit var presenter: BlackListPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}