package com.raspisanie.mai.ui.main.info.adv_list.create_adv_success

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_campus_map.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_success.*
import pro.midev.supersld.common.base.BaseFragment

class CreateAdvSuccessFragment : BaseFragment(R.layout.fragment_adv_create_success), MvpView {

    @InjectPresenter
    lateinit var presenter: CreateAdvSuccessPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSuccess.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}