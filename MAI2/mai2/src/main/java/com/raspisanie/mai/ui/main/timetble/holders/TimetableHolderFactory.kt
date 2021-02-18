package com.raspisanie.mai.ui.main.timetble.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder

@Suppress("UNCHECKED_CAST")
class TimetableHolderFactory {
    companion object {
        const val TITLE_ITEM = 0
        const val SUBJECT_ITEM = 1
        const val EVENT_ITEM = 2
        const val END_WEEK_ITEM = 3
        const val LINE_ITEM = 4

        fun create(type: Int, parent: ViewGroup, data: Any?): AbstractViewHolder? {
            when(type) {
                TITLE_ITEM -> return TitleHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_day_title, parent, false)
                )
                SUBJECT_ITEM -> return SubjectHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_subject, parent, false)
                )
                LINE_ITEM -> return LineHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_line, parent, false)
                )
                END_WEEK_ITEM -> return EndWeekHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_end_week, parent, false),
                        data as (Int, Any?) -> Unit
                )
            }
            return null
        }
    }
}