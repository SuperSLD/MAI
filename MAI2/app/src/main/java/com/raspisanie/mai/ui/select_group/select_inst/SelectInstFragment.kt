package com.raspisanie.mai.ui.select_group.select_inst

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemTopAndBottomPadding
import kotlinx.android.synthetic.main.fragment_select_year.*
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.fragment_select_year.btnNext as btnNext1

class SelectInstFragment : BaseFragment(R.layout.fragment_select_inst), MvpView {

    @InjectPresenter
    lateinit var presenter: SelectInstPresenter

    override var bottomNavigationViewVisibility = View.GONE

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