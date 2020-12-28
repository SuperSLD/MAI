package com.raspisanie.mai.ui.main.settings
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings), MvpView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}