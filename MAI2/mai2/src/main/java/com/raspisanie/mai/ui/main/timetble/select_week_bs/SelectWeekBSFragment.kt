package com.raspisanie.mai.ui.main.timetble.select_week_bs

import android.Manifest
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import com.raspisanie.mai.controllers.SelectWeekController
import com.raspisanie.mai.ui.main.settings.confirm_dialog.ConfirmBSFragment
import kotlinx.android.synthetic.main.bs_select_week.*

class SelectWeekBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_select_week), MvpView {

    @InjectPresenter
    lateinit var presenter: SelectWeekBSPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when(arguments?.getInt(ARG_WEEK)!!) {
            SelectWeekController.PREVIOUS_WEEK -> btnPrevious.visibility = View.GONE
            SelectWeekController.NEXT_WEEK -> btnNext.visibility = View.GONE
            SelectWeekController.THIS_WEEK -> btnThis.visibility = View.GONE
        }

        btnPrevious.setOnClickListener {
            presenter.select(SelectWeekController.PREVIOUS_WEEK)
            dismiss()
        }
        btnNext.setOnClickListener {
            presenter.select(SelectWeekController.NEXT_WEEK)
            dismiss()
        }
        btnThis.setOnClickListener {
            presenter.select(SelectWeekController.THIS_WEEK)
            dismiss()
        }
        btnOther.setOnClickListener {
            presenter.select(SelectWeekController.OTHER_WEEK)
            dismiss()
        }
    }

    companion object {
        const val ARG_WEEK = "arg_week"

        fun create(
                weekNumber: Int
        ): SelectWeekBSFragment {
            val fragment = SelectWeekBSFragment()

            val args = Bundle()
            args.putInt(ARG_WEEK, weekNumber)
            fragment.arguments = args

            return fragment
        }
    }
}