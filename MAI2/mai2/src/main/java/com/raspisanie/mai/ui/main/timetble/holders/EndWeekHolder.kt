package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import kotlinx.android.synthetic.main.item_timetable_end_week.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_end_week)
class EndWeekHolder(
        itemView: View
) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        with(itemView) {
            tvNext.setOnClickListener {
                makeEvent(TimetablePresenter.GO_TO_NEXT_WEEK)
            }
        }
    }
}