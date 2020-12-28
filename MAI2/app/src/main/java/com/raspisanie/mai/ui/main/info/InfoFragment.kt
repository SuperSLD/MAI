package com.raspisanie.mai.ui.main.info
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment

class InfoFragment : BaseFragment(R.layout.fragment_info), MvpView {

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}