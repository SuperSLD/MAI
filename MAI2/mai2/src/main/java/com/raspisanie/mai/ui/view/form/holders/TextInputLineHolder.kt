package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.lines.TextInputLine
import kotlinx.android.synthetic.main.item_line_text_input.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_text_input)
class TextInputLineHolder(itemView: View) : DFBaseHolder<TextInputLine>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(item: TextInputLine) {
        with(itemView) {
            etInputLine.setText(item.value)
            tvHint.text = item.hint + if (item.mandatory) "*" else ""
            etInputLine.inputType = item.inputType
            etInputLine.minLines = item.minLines
            etInputLine.hint = ""

            setOnClickListener {
                etInputLine.requestFocus()
            }

            val textWatcher = object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    item.value = etInputLine.text.toString()
                }
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            }

            etInputLine.addTextChangedListener(textWatcher)
        }
    }
}