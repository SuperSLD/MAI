package com.raspisanie.mai.ui.splash
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import pro.midev.supersld.common.base.BaseFragment

class SplashFragment : BaseFragment(R.layout.fragment_splash), MvpView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}