package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.LectorScheduleAdapter
import kotlinx.android.synthetic.main.item_button.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_button)
class ButtonHolder(
        itemView: View
) : DFBaseHolder<Int>(itemView) {
    override fun bind(item: Int) {
        with(itemView) {
            btn.setOnClickListener {
                makeEvent(LectorScheduleAdapter.EVENT_BUTTON_CLICK)
            }
        }
    }
}