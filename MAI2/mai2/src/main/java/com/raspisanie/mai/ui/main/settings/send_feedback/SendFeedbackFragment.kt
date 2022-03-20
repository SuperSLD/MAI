package com.raspisanie.mai.ui.main.settings.send_feedback

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.createFormParams
import com.raspisanie.mai.extesions.showToast
import com.yandex.metrica.YandexMetrica
import kotlinx.android.synthetic.main.fragment_feedback.*
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class SendFeedbackFragment : BaseFragment(R.layout.fragment_feedback), SendFeedbackView {

    @InjectPresenter
    lateinit var presenter: SendFeedbackPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
                title = R.string.settings_feedback_toolbar,
                back = { onBackPressed() }
        )

        with(vForm) {
            init(
                form = presenter.createForm(),
                childFragmentManager = childFragmentManager,
                context.createFormParams()
            )
            onFinish(presenter::sendFeedback)
            onToast {
                context.showToast(R.drawable.ic_close_toast, it)
            }
            addSystemBottomPadding()
        }
    }

    override fun onBackPressed() {
        YandexMetrica.reportEvent("SendFeedbackCancel")
        presenter.back()
    }
    override fun showErrorLoading() {}

    override fun toggleLoading(show: Boolean) {
        loading.visibility = if (show) View.VISIBLE else View.GONE
        vForm.visibility = if (show) View.GONE else View.VISIBLE
    }
}