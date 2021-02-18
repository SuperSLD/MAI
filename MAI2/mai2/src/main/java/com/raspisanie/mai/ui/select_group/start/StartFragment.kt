package com.raspisanie.mai.ui.select_group.start

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : BaseFragment(R.layout.fragment_start), MvpView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    override var bottomNavigationViewVisibility = View.GONE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnNext.setOnClickListener {
            presenter.goSelectYear()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}