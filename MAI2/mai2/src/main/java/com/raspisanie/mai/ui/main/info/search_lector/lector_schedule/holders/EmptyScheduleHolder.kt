package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getString
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import kotlinx.android.synthetic.main.item_lector_schedule_empty.view.*
import kotlinx.android.synthetic.main.item_timetable_end_week.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_lector_schedule_empty)
class EmptyScheduleHolder(
        itemView: View
) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val date = data as String
        with(itemView) {
            tvInfoText.text =
                context.getString(
                    R.string.info_lector_schedule_empty,
                    date.ifEmpty { getString(R.string.info_lector_schedule_today) }
                )
        }
    }
}