package com.raspisanie.mai.ui.main.timetble.select_week_bs

import android.Manifest
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment

class SelectWeekBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_select_week), MvpView {

    @InjectPresenter
    lateinit var presenter: SelectWeekBSPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}