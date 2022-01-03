package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.raspisanie.mai.ui.view.form.lines.CheckBoxLine
import kotlinx.android.synthetic.main.item_line_check_box.view.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_check_box)
class CheckBoxLineHolder(itemView: View) : DFBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val flag = data as CheckBoxLine
        with(itemView) {
            cbFlag.text = flag.text + if(flag.mandatory) "*" else ""
            cbFlag.isChecked = flag.checked
            cbFlag.setOnCheckedChangeListener { _, isChecked ->
                flag.checked = isChecked
            }
        }
    }
}