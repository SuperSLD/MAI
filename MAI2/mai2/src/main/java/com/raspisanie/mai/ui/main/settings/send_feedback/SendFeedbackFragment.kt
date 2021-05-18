package com.raspisanie.mai.ui.main.settings.send_feedback

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.yandex.metrica.YandexMetrica
import kotlinx.android.synthetic.main.fragment_feedback.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment

class SendFeedbackFragment : BaseFragment(R.layout.fragment_feedback), SendFeedbackView {

    @InjectPresenter
    lateinit var presenter: SendFeedbackPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
                title = R.string.settings_feedback_toolbar,
                back = { onBackPressed() }
        )

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { checkData() }
            override fun afterTextChanged(s: Editable?) {}
        }
        btnSend.setOnClickListener {
            YandexMetrica.reportEvent("SendFeedbackClick")
            presenter.sendFeedback(
                    name = etName.text.toString(),
                    email = etEmail.text.toString(),
                    message = etMessage.text.toString()
            )
        }

        btnCancel.setOnClickListener {
            YandexMetrica.reportEvent("SendFeedbackCancel")
            presenter.back()
        }

        etName.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etMessage.addTextChangedListener(textWatcher)

        checkData()
    }

    override fun onBackPressed() {
        YandexMetrica.reportEvent("SendFeedbackCancel")
        presenter.back()
    }

    private fun checkData() {
        val valid = etEmail.text.isNotEmpty() && etMessage.text.toString().isNotEmpty() && etName.text.isNotEmpty()
        btnSend.isEnabled = valid
    }

    override fun showErrorLoading() {}

    override fun toggleLoading(show: Boolean) {
        loading.visibility = if (show) View.VISIBLE else View.GONE
        nested.visibility = if (show) View.GONE else View.VISIBLE
    }
}