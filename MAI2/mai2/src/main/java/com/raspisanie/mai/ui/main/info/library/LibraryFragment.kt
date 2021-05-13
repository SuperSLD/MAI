package com.raspisanie.mai.ui.main.info.library

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_campus_map.*
import kotlinx.android.synthetic.main.fragment_library.*
import pro.midev.supersld.common.base.BaseFragment

class LibraryFragment : BaseFragment(R.layout.fragment_library), MvpView {

    @InjectPresenter
    lateinit var presenter: LibraryPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_library,
            back = {onBackPressed()}
        )
    }

    override fun onBackPressed() {
        presenter.back()
    }
}