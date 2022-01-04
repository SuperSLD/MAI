package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import kotlinx.android.synthetic.main.item_timetable_end_week.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_end_week)
class EndWeekHolder(
        itemView: View
) : DFBaseHolder<Int>(itemView) {
    override fun bind(item: Int) {
        with(itemView) {
            tvNext.setOnClickListener {
                makeEvent(TimetablePresenter.GO_TO_NEXT_WEEK)
            }
        }
    }
}