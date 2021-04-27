package com.raspisanie.mai.ui.main.settings.confirm_dialog

import android.Manifest
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_confirm_delete.*

class ConfirmBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_confirm_delete), MvpView {

    @InjectPresenter
    lateinit var presenter: ConfirmBSPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvTitle.text = arguments?.getString(ARG_TEXT)
        tvAction.text = arguments?.getString(ARG_ACTION)

        btnAccept.setOnClickListener {
            presenter.confirm()
            dismiss()
        }

        btnCancel.setOnClickListener {
            presenter.cancel()
            dismiss()
        }
    }

    companion object {
        const val ARG_TEXT = "arg_text"
        const val ARG_ACTION = "arg_action"

        fun create(
                title: String,
                action: String
        ): ConfirmBSFragment {
            val fragment = ConfirmBSFragment()

            val args = Bundle()
            args.putString(ARG_TEXT, title)
            args.putString(ARG_ACTION, action)
            fragment.arguments = args

            return fragment
        }
    }
}