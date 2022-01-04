package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getString
import kotlinx.android.synthetic.main.item_lector_schedule_empty.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_lector_schedule_empty)
class EmptyScheduleHolder(
        itemView: View
) : DFBaseHolder<String>(itemView) {
    override fun bind(item: String) {
        with(itemView) {
            tvInfoText.text =
                context.getString(
                    R.string.info_lector_schedule_empty,
                    item.ifEmpty { getString(R.string.info_lector_schedule_today) }
                )
        }
    }
}