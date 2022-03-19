package com.raspisanie.mai.ui.main.timetble

import com.raspisanie.mai.domain.models.DayLocal
import com.raspisanie.mai.domain.models.WeekLocal
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.HolderFactory


class TimetableAdapter(
        val onItemClick: (Int, Any?) -> Unit
) : DiffAdapter() {

    companion object {
        const val TITLE_ITEM = 1001
        const val SUBJECT_ITEM = 1002
        const val END_WEEK_ITEM = 1003
        const val LINE_ITEM = 1004
    }

    override fun initFactory() =  HolderFactory(hashMapOf(
                TITLE_ITEM to TitleHolder::class.java,
                SUBJECT_ITEM to SubjectHolder::class.java,
                LINE_ITEM to LineHolder::class.java,
                END_WEEK_ITEM to EndWeekHolder::class.java
            )
        ).onEvent{ id, data ->
            onItemClick(id, data)
        }

    fun getItemByPosition(position: Int): Any {
        return getList()[position].second
    }

    fun getItemPosition(date: String): Int {
        val list = getList()
        for (i in list.indices) {
            if (list[i].second is DayLocal) {
                val day = list[i].second as DayLocal
                if (day.date == date) {
                    return i
                }
            }
        }
        return 0
    }

    fun itemsSize() = getList().size

    fun addData(data: WeekLocal, last: Boolean = false) {
        val list = getList()
        list.clear()
        for (day in data.days) {
            list.add(Pair(TITLE_ITEM, day))
            for (subject in day.subjects) {
                list.add(Pair(SUBJECT_ITEM, subject))
            }
            if (data.days.indexOf(day) != data.days.size - 1) {
                list.add(Pair(LINE_ITEM, 0))
            }
        }
        if (!last) list.add(Pair(END_WEEK_ITEM, 0))
        notifyDataSetChanged()
    }
}