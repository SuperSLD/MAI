package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.local.DayLocal
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import java.util.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_day_title)
class TitleHolder(itemView: View) : DFBaseHolder(itemView) {
    private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

    override fun bind(data: Any?) {
        val day = data as DayLocal
        with(itemView) {
            val dateString = day.date.split(" - ")[0].fromFormatToFormat("dd.MM.yyyy", "dd.MM")
            tvDate.text = dateString

            val calendar = day.date.parseCalendarByFormat("dd.MM.yyyy")
            tvDayName.text = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]

            //TODO потом сделать отображение текущего дня.
            cvNow.visibility = View.GONE
        }
    }
}