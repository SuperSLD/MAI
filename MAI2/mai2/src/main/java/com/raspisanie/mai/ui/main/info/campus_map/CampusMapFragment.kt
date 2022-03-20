package com.raspisanie.mai.ui.main.info.campus_map

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_campus_map.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopAndBottomPadding
import online.jutter.supersld.extensions.addSystemTopPadding

class CampusMapFragment : BaseFragment(R.layout.fragment_campus_map), MvpView {

    @InjectPresenter
    lateinit var presenter: CampusMapPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvBack.setOnClickListener { onBackPressed() }
        vgButtonContainer.addSystemTopPadding()
        vPhoto.addSystemTopAndBottomPadding()
    }

    override fun onBackPressed() {
        presenter.back()
    }
}