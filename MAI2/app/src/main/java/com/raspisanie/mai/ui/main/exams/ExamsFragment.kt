package com.raspisanie.mai.ui.main.exams
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment

class ExamsFragment : BaseFragment(R.layout.fragment_exams), MvpView {

    @InjectPresenter
    lateinit var presenter: ExamsPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}