package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.TeacherLocal
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.ButtonHolder
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.EmptyScheduleHolder
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.HeaderLectorScheduleHolder
import com.raspisanie.mai.ui.main.timetble.holders.SubjectHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.HolderFactory


class LectorScheduleAdapter(
    val openCalendar: ()->Unit
) : DiffAdapter() {

    companion object {
        const val HEADER = 0
        const val SUBJECT_ITEM = 1
        const val EMPTY = 2
        const val BUTTON = 3

        const val EVENT_BUTTON_CLICK = 0
    }

    override fun initFactory() =
        HolderFactory(hashMapOf(
                HEADER to HeaderLectorScheduleHolder::class.java,
                SUBJECT_ITEM to SubjectHolder::class.java,
                EMPTY to EmptyScheduleHolder::class.java,
                BUTTON to ButtonHolder::class.java
            )
        ).onEvent { id, _ ->
            when(id) {
                EVENT_BUTTON_CLICK -> openCalendar()
            }
        }

    fun addData(teacher: TeacherLocal, day: DayLocal) {
        val list = getList()
        list.clear()
        list.add(
            Pair(HEADER, hashMapOf("name" to teacher.name, "date" to day.date))
        )
        if (day.subjects.isEmpty()) {
            list.add(Pair(EMPTY, day.date))
        } else for (subject in day.subjects) {
            list.add(Pair(SUBJECT_ITEM, subject))
        }
        list.add(Pair(BUTTON, 0))
        notifyDataSetChanged()
    }
}