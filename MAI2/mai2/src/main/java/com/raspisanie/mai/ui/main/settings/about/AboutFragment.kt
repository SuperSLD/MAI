package com.raspisanie.mai.ui.main.settings.about

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_about.*
import online.jutter.supersld.common.base.BaseFragment

class AboutFragment : BaseFragment(R.layout.fragment_about), MvpView {

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}