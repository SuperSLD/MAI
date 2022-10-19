package com.raspisanie.mai.ui.main.info.roadmap.navigation.pointtype

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_confirm_delete.*
import kotlinx.android.synthetic.main.bs_point_type.*

class PointTypeBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_point_type), MvpView {

    @InjectPresenter
    lateinit var presenter: PointTypeBSPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMap.setOnClickListener {
            presenter.mapPoint()
            dismiss()
        }
        btnRoom.setOnClickListener {
            presenter.roomPoint()
            dismiss()
        }
    }
}