package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getColor
import kotlinx.android.synthetic.main.view_form.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.raspisanie.mai.ui.view.form.lines.RadioLine
import kotlinx.android.synthetic.main.item_line_radio.view.*


@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_radio)
class RadioLineHolder(itemView: View) : DFBaseHolder(itemView) {

    private val TEXT_COLOR = itemView.getColor(R.color.colorTextSecondary)

    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val radio = data as RadioLine
        with(itemView) {
            tvName.text = radio.title

            var rprms: RadioGroup.LayoutParams?

            for (i in radio.list.indices) {
                val radioButton = RadioButton(context)
                radioButton.text = radio.list[i].text
                radioButton.id = radio.list[i].id.hashCode()
                radioButton.setTextColor(TEXT_COLOR)
                rprms = RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                rprms.bottomMargin = 16
                vgRadio.addView(radioButton, rprms)
            }

            vgRadio.check(radio.selectedItem.hashCode())
            vgRadio.setOnCheckedChangeListener {_, checkedId ->
                radio.setSelected(checkedId)
            }
        }
    }
}