package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.SubjectLocal
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_subject)
class SubjectHolder(itemView: View) : DFBaseHolder<SubjectLocal>(itemView) {

    private val selected = ContextCompat.getColor(itemView.context, R.color.colorPrimarySecondary)
    private val unselected = ContextCompat.getColor(itemView.context, R.color.colorBorder)

    @SuppressLint("SetTextI18n")
    override fun bind(item: SubjectLocal) {
        with(itemView) {
            tvLessonNumber.text = item.number
            tvName.text = item.name
            tvTime.text = "${item.timeStart} - ${item.timeEnd}"
            tvTeacher.text = item.teacher.name
            tvTeacher.visibility = if(item.teacher.name.isEmpty()) View.GONE else View.VISIBLE
            tvTeacher.setOnClickListener {
                makeEvent(TimetablePresenter.OPEN_LECTOR_SCHEDULE, item)
            }
            tvLocation.text = item.room.name
            tvType.text = item.type

            vgOnlineLink.visibility = if (item.link.isNotEmpty()) View.VISIBLE else View.GONE
            cvSubject.strokeColor = if (item.link.isNotEmpty())
                selected else unselected
            vgOnlineLink.setOnClickListener {
                makeEvent(TimetablePresenter.OPEN_ONLINE, item.link)
            }
        }
    }
}