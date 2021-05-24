package com.raspisanie.mai.ui.main.info.campus_map

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_campus_map.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemTopAndBottomPadding
import pro.midev.supersld.extensions.addSystemTopPadding

class CampusMapFragment : BaseFragment(R.layout.fragment_campus_map), MvpView {

    @InjectPresenter
    lateinit var presenter: CampusMapPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cvBack.setOnClickListener { onBackPressed() }
        vgButtonContainer.addSystemTopPadding()
        vPhoto.addSystemTopAndBottomPadding()
    }

    override fun onBackPressed() {
        presenter.back()
    }
}