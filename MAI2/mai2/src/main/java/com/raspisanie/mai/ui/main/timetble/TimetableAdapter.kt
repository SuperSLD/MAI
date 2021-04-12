package com.raspisanie.mai.ui.main.timetble

import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory


class TimetableAdapter(
        val onItemClick: (Int, Any?) -> Unit
) : DifAdapter() {

    companion object {
        const val TITLE_ITEM = 0
        const val SUBJECT_ITEM = 1
        const val EVENT_ITEM = 2
        const val END_WEEK_ITEM = 3
        const val LINE_ITEM = 4
    }

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
                TITLE_ITEM to TitleHolder::class.java,
                SUBJECT_ITEM to SubjectHolder::class.java,
                //EVENT_ITEM to EVE::class.java,
                LINE_ITEM to LineHolder::class.java,
                END_WEEK_ITEM to EndWeekHolder::class.java
            )
        ).onEvent{ id, data ->

        }
    }

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()



    override fun onBindViewHolder(holder: DFBaseHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun getItemByPosition(position: Int): Any? {
        return list[position].second
    }

    fun getItemPosition(date: String): Int {
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

    fun addData(data: WeekLocal) {
        this.list.clear()
        for (day in data.days) {
            this.list.add(Pair(TITLE_ITEM, day))
            for (subject in day.subjects) {
                this.list.add(Pair(SUBJECT_ITEM, subject))
            }
            if (data.days.indexOf(day) != data.days.size - 1) {
                this.list.add(Pair(LINE_ITEM, null))
            }
        }
        this.list.add(Pair(END_WEEK_ITEM, null))
        notifyDataSetChanged()
    }
}