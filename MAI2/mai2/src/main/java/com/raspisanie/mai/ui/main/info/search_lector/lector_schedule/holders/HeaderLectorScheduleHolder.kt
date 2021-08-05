package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getString
import kotlinx.android.synthetic.main.item_lector_schedule_header.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import java.util.*
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_lector_schedule_header)
class HeaderLectorScheduleHolder(
        itemView: View
) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val hashMap = data as HashMap<String, String>
        with(itemView) {
            tvName.text = hashMap["name"]
            tvDate.text =
                context.getString(
                    R.string.info_lector_schedule_header_bottom,
                    if (hashMap["date"] == "") getString(R.string.info_lector_schedule_today).toLowerCase(Locale.ROOT) else hashMap["date"]
                )
        }
    }
}