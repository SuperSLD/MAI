package com.raspisanie.mai.ui.main.timetble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.WeekHuman
import com.raspisanie.mai.ui.main.timetble.holders.TimetableHolderFactory
import com.raspisanie.mai.ui.main.timetble.holders.TitleHolder
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import kotlinx.android.synthetic.main.item_timetable_day_title.view.tvDayName
import kotlinx.android.synthetic.main.item_timetable_header.view.*
import java.util.*


class TimetableHeaderAdapter : RecyclerView.Adapter<AbstractViewHolder>() {

    private val list = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return ItemHeaderHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_header, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addData(data: WeekHuman) {
        this.list.clear()

        for (day in data.days) {
            this.list.add(day)
        }

        notifyDataSetChanged()
    }

    private inner class ItemHeaderHolder(itemView: View) : AbstractViewHolder(itemView) {
        private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

        override fun bind(data: Any?) {
            val day = data as DayHuman
            with(itemView) {
                if (list.indexOf(day) == 0) {
                    lPadding.visibility = View.VISIBLE
                } else {
                    lPadding.visibility = View.GONE
                }

                val calendar = day.date.parseCalendarByFormat("dd.MM.yyyy")
                tvDayName.text = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]

                if (list.indexOf(day) == list.size - 1) {
                    lPaddingEnd.visibility = View.VISIBLE
                } else {
                    lPaddingEnd.visibility = View.GONE
                }
            }
        }
    }
}