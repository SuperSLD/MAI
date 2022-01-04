package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.lines.CheckBoxLine
import kotlinx.android.synthetic.main.item_line_check_box.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_check_box)
class CheckBoxLineHolder(itemView: View) : DFBaseHolder<CheckBoxLine>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(item: CheckBoxLine) {
        with(itemView) {
            cbFlag.text = item.text + if(item.mandatory) "*" else ""
            cbFlag.isChecked = item.checked
            cbFlag.setOnCheckedChangeListener { _, isChecked ->
                item.checked = isChecked
            }
        }
    }
}