package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.raspisanie.mai.ui.view.form.lines.TextLine
import kotlinx.android.synthetic.main.item_line_text.view.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_text)
class TextLineHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val text = data as TextLine
        with(itemView) {
            tvText.text = text.text
        }
    }
}