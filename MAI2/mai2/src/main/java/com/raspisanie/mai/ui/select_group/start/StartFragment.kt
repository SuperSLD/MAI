package com.raspisanie.mai.ui.select_group.start

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_start.*
import online.jutter.supersld.common.base.BaseFragment

class StartFragment : BaseFragment(R.layout.fragment_start), MvpView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext.setOnClickListener {
            presenter.goSelectYear()
        }

        btnSkip.setOnClickListener {
            presenter.skip()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}