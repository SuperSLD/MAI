package com.raspisanie.mai.ui.main.settings.feedback_response.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.settings.feedback_response.ResponseListAdapter
import kotlinx.android.synthetic.main.item_new_question.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_new_question)
class NewQuestionHolder(itemView: View) : DFBaseHolder<Int>(itemView) {
    override fun bind(item: Int) {
        with(itemView) {
            btnNew.setOnClickListener {
                makeEvent(ResponseListAdapter.NEW_QUESTION_EVENT)
            }
        }
    }
}