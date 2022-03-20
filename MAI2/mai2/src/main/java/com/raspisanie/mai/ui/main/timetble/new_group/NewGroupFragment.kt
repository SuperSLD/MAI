package com.raspisanie.mai.ui.main.timetble.new_group

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_success.*
import online.jutter.supersld.common.base.BaseFragment

class NewGroupFragment : BaseFragment(R.layout.fragment_new_group), MvpView {

    @InjectPresenter
    lateinit var presenter: NewGroupPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSuccess.setOnClickListener { presenter.openStart() }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}