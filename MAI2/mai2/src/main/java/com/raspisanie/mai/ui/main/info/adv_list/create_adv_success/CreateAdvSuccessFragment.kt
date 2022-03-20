package com.raspisanie.mai.ui.main.info.adv_list.create_adv_success

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_success.*
import online.jutter.supersld.common.base.BaseFragment

class CreateAdvSuccessFragment : BaseFragment(R.layout.fragment_adv_create_success), MvpView {

    @InjectPresenter
    lateinit var presenter: CreateAdvSuccessPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSuccess.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}