package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.raspisanie.mai.R
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.raspisanie.mai.ui.view.form.lines.TextInputLine
import kotlinx.android.synthetic.main.item_line_text_input.view.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_text_input)
class TextInputLineHolder(itemView: View) : DFBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val textInputLine = data as TextInputLine
        with(itemView) {
            etInputLine.setText(textInputLine.value)
            tvHint.text = textInputLine.hint + if (textInputLine.mandatory) "*" else ""
            etInputLine.inputType = textInputLine.inputType
            etInputLine.minLines = textInputLine.minLines
            etInputLine.hint = ""

            setOnClickListener {
                etInputLine.requestFocus()
            }

            val textWatcher = object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    textInputLine.value = etInputLine.text.toString()
                }
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            }

            etInputLine.addTextChangedListener(textWatcher)
        }
    }
}