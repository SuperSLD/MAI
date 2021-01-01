package com.raspisanie.mai.ui.main.timetble.holders

import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.models.human.DayHuman
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import java.util.*

class TitleHolder(itemView: View) : AbstractViewHolder(itemView) {
    private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

    override fun bind(data: Any?) {
        val day = data as DayHuman
        with(itemView) {
            tvDate.text = day.date

            //tvMonthTitle.text = data as String
        }
    }
}