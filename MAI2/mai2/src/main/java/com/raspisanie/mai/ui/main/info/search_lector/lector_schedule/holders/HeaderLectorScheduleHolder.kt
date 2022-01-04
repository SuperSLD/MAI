package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getString
import kotlinx.android.synthetic.main.item_lector_schedule_header.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout
import java.util.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_lector_schedule_header)
class HeaderLectorScheduleHolder(
        itemView: View
) : DFBaseHolder<HashMap<String, String>>(itemView) {
    override fun bind(item: HashMap<String, String>) {
        with(itemView) {
            tvName.text = item["name"]
            tvDate.text =
                context.getString(
                    R.string.info_lector_schedule_header_bottom,
                    if (item["date"] == "") getString(R.string.info_lector_schedule_today).toLowerCase(Locale.ROOT) else item["date"]
                )
        }
    }
}