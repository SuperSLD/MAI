package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.domain.models.DayLocal
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout
import java.util.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_day_title)
class TitleHolder(itemView: View) : DFBaseHolder<DayLocal>(itemView) {
    private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

    override fun bind(item: DayLocal) {
        with(itemView) {
            val dateString = item.date.fromFormatToFormat("dd.MM.yyyy", "dd.MM")
            tvDate.text = dateString

            val calendar = item.date.parseCalendarByFormat("dd.MM.yyyy")
            tvDayName.text = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]

            val current = Calendar.getInstance()
            cvNow.visibility = if (calendar.get(Calendar.DAY_OF_YEAR) == current.get(Calendar.DAY_OF_YEAR)) View.VISIBLE else View.GONE
        }
    }
}