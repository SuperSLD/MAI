package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.TeacherLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.models.realm.DayRealm
import com.raspisanie.mai.models.realm.WeekRealm
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.ButtonHolder
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.EmptyScheduleHolder
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders.HeaderLectorScheduleHolder
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory
import kotlin.math.E


class LectorScheduleAdapter(
    val openCalendar: ()->Unit
) : DifAdapter() {

    companion object {
        const val HEADER = 0
        const val SUBJECT_ITEM = 1
        const val EMPTY = 2
        const val BUTTON = 3

        const val EVENT_BUTTON_CLICK = 0
    }

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
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

    fun addData(teacher: TeacherLocal, day: DayLocal) {
        this.list.clear()
        this.list.add(
            Pair(HEADER, hashMapOf("name" to teacher.name, "date" to day.date))
        )
        if (day.subjects.isEmpty()) {
            this.list.add(Pair(EMPTY, day.date))
        } else for (subject in day.subjects) {
            this.list.add(Pair(SUBJECT_ITEM, subject))
        }
        this.list.add(Pair(BUTTON, null))
        notifyDataSetChanged()
    }
}