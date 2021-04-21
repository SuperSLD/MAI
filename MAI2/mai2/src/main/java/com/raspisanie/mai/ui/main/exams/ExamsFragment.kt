package com.raspisanie.mai.ui.main.exams
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_exams.*
import pro.midev.supersld.common.base.BaseFragment

class ExamsFragment : BaseFragment(R.layout.fragment_exams), MvpView {

    @InjectPresenter
    lateinit var presenter: ExamsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vToolbar.init(
                title = R.string.exams_title
        )
    }

    override fun onBackPressed() {
        presenter.back()
    }
}