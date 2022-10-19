package com.raspisanie.mai.ui.main.settings.feedback_response.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.raspisanie.mai.R
import com.raspisanie.mai.data.net.models.FeedbackResponse
import kotlinx.android.synthetic.main.item_question_response.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_question_response)
class FeedbackResponseHolder(itemView: View) : DFBaseHolder<FeedbackResponse>(itemView) {

    override fun bind(item: FeedbackResponse) {
        with(itemView) {
            tvAnswerText.text = item.message
            if (item.response.isEmpty()) {
                icStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_resp_no))
                tvResponseText.visibility = View.GONE
                vDivider.visibility = View.GONE
                tvStatus.setText(R.string.settings_feedback_waiting_response)
            } else {
                tvStatus.setText(R.string.settings_feedback_response)
                icStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_resp_ok))
                tvResponseText.visibility = View.VISIBLE
                vDivider.visibility = View.VISIBLE
                tvResponseText.text = item.response.first().response
            }
        }
    }
}