package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.lines.TextLine
import kotlinx.android.synthetic.main.item_line_text.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_text)
class TextLineHolder(itemView: View) : DFBaseHolder<TextLine>(itemView) {
    override fun bind(item: TextLine) {
        with(itemView) {
            tvText.text = item.text
        }
    }
}