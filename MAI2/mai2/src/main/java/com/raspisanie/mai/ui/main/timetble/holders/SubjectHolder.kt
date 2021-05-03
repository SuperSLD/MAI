package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.local.SubjectLocal
import com.raspisanie.mai.models.realm.SubjectRealm
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import java.util.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_subject)
class SubjectHolder(itemView: View) : DFBaseHolder(itemView) {

    private val selected = ContextCompat.getColor(itemView.context, R.color.colorPrimarySecondary)
    private val unselected = ContextCompat.getColor(itemView.context, R.color.colorBorder)

    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val subject = data as SubjectLocal
        with(itemView) {
            tvLessonNumber.text = subject.number
            tvName.text = subject.name
            tvTime.text = "${subject.timeStart} - ${subject.timeEnd}"
            tvTeacher.text = subject.teacher.name
            tvTeacher.visibility = if(subject.teacher.name.isEmpty()) View.GONE else View.VISIBLE
            tvLocation.text = subject.room.name
            tvType.text = subject.type

            val current = Calendar.getInstance()
            val start = subject.timeStart.parseCalendarByFormat("HH:mm")
            val end = subject.timeEnd.parseCalendarByFormat("HH:mm")

            (this as MaterialCardView).strokeColor = if (current.after(start) && current.before(end))
                selected else unselected
        }
    }
}