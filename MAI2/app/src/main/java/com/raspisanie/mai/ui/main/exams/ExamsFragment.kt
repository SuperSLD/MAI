package com.raspisanie.mai.ui.main.exams
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_timetable.*

class ExamsFragment : BaseFragment(R.layout.fragment_exams), MvpView {

    @InjectPresenter
    lateinit var presenter: ExamsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.exams_title, 0, 0, 0)

    }

    override fun onBackPressed() {
        presenter.back()
    }
}