package com.raspisanie.mai.ui.select_group.select_group

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_select_group.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemTopAndBottomPadding

class SelectGroupFragment : BaseFragment(R.layout.fragment_select_group), MvpView {

    @InjectPresenter
    lateinit var presenter: SelectGroupPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lContainer.addSystemTopAndBottomPadding()

        cvBack.setOnClickListener {
            onBackPressed()
        }

        btnNext.setOnClickListener {
            presenter.next()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}