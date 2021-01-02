package com.raspisanie.mai.ui.main.timetble.holders

import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.human.DayHuman
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import java.util.*

class EndWeekHolder(
        itemView: View,
        var click: (Int, Any?) -> Unit
) : AbstractViewHolder(itemView) {

    override fun bind(data: Any?) {

    }
}