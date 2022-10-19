package com.raspisanie.mai.ui.main.settings.feedback_response

import com.raspisanie.mai.data.net.models.FeedbackResponse
import com.raspisanie.mai.ui.main.settings.feedback_response.holders.FeedbackEmptyHolder
import com.raspisanie.mai.ui.main.settings.feedback_response.holders.FeedbackResponseHolder
import com.raspisanie.mai.ui.main.settings.feedback_response.holders.NewQuestionHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderFactory


class ResponseListAdapter(
    private val newQuestion: ()->Unit,
) : DiffAdapter() {

    companion object {
        const val NEW_QUESTION_ITEM = 1001
        const val EMPTY_ITEM = 1002
        const val RESPONSE_ITEM = 1003

        const val NEW_QUESTION_EVENT = 2001
    }

    override fun initFactory() = HolderFactory(hashMapOf(
                NEW_QUESTION_ITEM to NewQuestionHolder::class.java,
                EMPTY_ITEM to FeedbackEmptyHolder::class.java,
                RESPONSE_ITEM to FeedbackResponseHolder::class.java,
            )
        ).onEvent { id, _ ->
            when(id) {
                NEW_QUESTION_EVENT -> newQuestion()
            }
        }

    override fun onBindViewHolder(holder: DFBaseHolder<Any>, position: Int) {
        holder.pubBind(if (getList().size > position) getList()[position].second else 0)
    }

    override fun getItemCount(): Int = getList().size

    override fun getItemViewType(position: Int) = getList()[position].first

    fun addData(data: List<FeedbackResponse>) {
        val list = getList()
        list.clear()
        getList().add(Pair(NEW_QUESTION_ITEM, 0))
        if (data.isEmpty()) {
            list.add(Pair(EMPTY_ITEM, 0))
        } else {
            data.forEach { r ->
                list.add(Pair(RESPONSE_ITEM, r))
            }
        }
        notifyDataSetChanged()
    }
}