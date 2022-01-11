package com.raspisanie.mai.ui.main.info.adv_list.add_adv

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.createFormParams
import com.raspisanie.mai.extesions.showToast
import kotlinx.android.synthetic.main.fragment_adv_add.*
import kotlinx.android.synthetic.main.fragment_canteens.vToolbar
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class AddAdvFragment : BaseFragment(R.layout.fragment_adv_add), AddAdvView {

    @InjectPresenter
    lateinit var presenter: AddAdvPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            init(
                title = R.string.adv_add_title,
                back = { onBackPressed() }
            )
        }

        with(vForm) {
            init(
                form = presenter.createForm(),
                childFragmentManager = childFragmentManager,
                context.createFormParams()
            )
            onFinish(presenter::sendForm)
            onToast {
                context.showToast(R.drawable.ic_close_toast, it)
            }
            addSystemBottomPadding()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showErrorLoading() {}

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        vForm.visibility = if (show) View.GONE else View.VISIBLE
    }
}