package com.raspisanie.mai.ui.main.info.creative

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_campus_map.*
import kotlinx.android.synthetic.main.fragment_library.*
import pro.midev.supersld.common.base.BaseFragment

class CreativeFragment : BaseFragment(R.layout.fragment_creative), MvpView {

    @InjectPresenter
    lateinit var presenter: CreativePresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_creative,
            back = {onBackPressed()}
        )
    }

    override fun onBackPressed() {
        presenter.back()
    }
}