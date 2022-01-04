package com.raspisanie.mai.ui.main.timetble.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_timetable_line)
class LineHolder(itemView: View) : DFBaseHolder<Int>(itemView) {

    override fun bind(item: Int) {}
}