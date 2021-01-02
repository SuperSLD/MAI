package com.raspisanie.mai.ui.main.timetble.holders

import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.SubjectHuman
import kotlinx.android.synthetic.main.item_timetable_day_title.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import java.util.*

class SubjectHolder(itemView: View) : AbstractViewHolder(itemView) {

    override fun bind(data: Any?) {
        val subject = data as SubjectHuman
        with(itemView) {
            tvName.text = subject.name
            tvTime.text = subject.time
            tvTeacher.text = subject.teacher
            tvLocation.text = subject.location
            tvType.text = subject.type
        }
    }
}