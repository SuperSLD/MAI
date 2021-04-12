package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.SubjectLocal
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_subject)
class SubjectHolder(itemView: View) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        val subject = data as SubjectLocal
        with(itemView) {
            tvName.text = subject.name
            tvTime.text = subject.time
            tvTeacher.text = subject.teacher
            tvLocation.text = subject.location
            tvType.text = subject.type
        }
    }
}