package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getColor
import com.raspisanie.mai.ui.view.form.lines.RadioLine
import kotlinx.android.synthetic.main.item_line_radio.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_radio)
class RadioLineHolder(itemView: View) : DFBaseHolder<RadioLine>(itemView) {

    private val TEXT_COLOR = itemView.getColor(R.color.colorTextSecondary)

    @SuppressLint("SetTextI18n")
    override fun bind(item: RadioLine) {
        with(itemView) {
            tvName.text = item.title

            var rprms: RadioGroup.LayoutParams?

            for (i in item.list.indices) {
                val radioButton = RadioButton(context)
                radioButton.text = item.list[i].text
                radioButton.id = item.list[i].id.hashCode()
                radioButton.setTextColor(TEXT_COLOR)
                rprms = RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                rprms.bottomMargin = 16
                vgRadio.addView(radioButton, rprms)
            }

            vgRadio.check(item.selectedItem.hashCode())
            vgRadio.setOnCheckedChangeListener {_, checkedId ->
                item.setSelected(checkedId)
            }
        }
    }
}