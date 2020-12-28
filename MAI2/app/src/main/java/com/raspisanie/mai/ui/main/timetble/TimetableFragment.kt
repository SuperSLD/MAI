package com.raspisanie.mai.ui.main.timetble
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment

class TimetableFragment : BaseFragment(R.layout.fragment_timetable), MvpView {

    @InjectPresenter
    lateinit var presenter: TimetablePresenter

    override fun onBackPressed() {
        presenter.back()
    }
}