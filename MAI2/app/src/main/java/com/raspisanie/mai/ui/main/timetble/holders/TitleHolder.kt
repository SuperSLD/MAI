package com.raspisanie.mai.ui.main.timetble.holders

import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.human.DayHuman
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import java.util.*

class TitleHolder(itemView: View) : AbstractViewHolder(itemView) {
    private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

    override fun bind(data: Any?) {
        val day = data as DayHuman
        with(itemView) {
            val dateString = "${day.date.split(".")[0]}.${day.date.split(".")[1]}"
            tvDate.text = dateString

            val calendar = day.date.parseCalendarByFormat("dd.MM.yyyy")
            tvDayName.text = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]

            //TODO потом сделать отображение текущего дня.
            cvNow.visibility = View.GONE
        }
    }
}