package com.raspisanie.mai.ui.main.timetble.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder

class TimetableHolderFactory {
    companion object {
        const val TITLE_ITEM = 0
        const val SUBJECT_ITEM = 1
        const val EVENT_ITEM = 2
        const val END_WEEK_ITEM = 3

        fun create(type: Int, parent: ViewGroup, data: Any?): AbstractViewHolder? {
            when(type) {
                TITLE_ITEM -> return TitleHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_day_title, parent, false)
                )

            }
            return null
        }
    }
}