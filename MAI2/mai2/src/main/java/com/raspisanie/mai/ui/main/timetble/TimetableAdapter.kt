package com.raspisanie.mai.ui.main.timetble

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.WeekHuman
import com.raspisanie.mai.ui.main.timetble.holders.TimetableHolderFactory
import kotlin.time.seconds


class TimetableAdapter(
        val onItemClick: (Int, Any?) -> Unit
) : RecyclerView.Adapter<AbstractViewHolder>() {

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return TimetableHolderFactory.create(viewType, parent, onItemClick)!!
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
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
            if (list[i].second is DayHuman) {
                val day = list[i].second as DayHuman
                if (day.date == date) {
                    return i
                }
            }
        }
        return 0
    }

    fun addData(data: WeekHuman) {
        this.list.clear()
        for (day in data.days) {
            this.list.add(Pair(TimetableHolderFactory.TITLE_ITEM, day))
            for (subject in day.subjects) {
                this.list.add(Pair(TimetableHolderFactory.SUBJECT_ITEM, subject))
            }
            if (data.days.indexOf(day) != data.days.size - 1) {
                this.list.add(Pair(TimetableHolderFactory.LINE_ITEM, null))
            }
        }
        this.list.add(Pair(TimetableHolderFactory.END_WEEK_ITEM, null))
        notifyDataSetChanged()
    }
}