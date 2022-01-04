package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.FormLinesAdapter
import com.raspisanie.mai.ui.view.form.lines.SelectorLine
import com.raspisanie.mai.ui.view.form.selector_bs.SelectorBSFragment
import kotlinx.android.synthetic.main.item_line_selector.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_selector)
class SelectorLineHolder(itemView: View) : DFBaseHolder<SelectorLine>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(item: SelectorLine) {
        with(itemView) {
            tvName.text = item.title + if(item.mandatory) "*" else ""
            tvSelected.text = selectedString(item, this)

            cvSelector.setOnClickListener {
                val dialog = SelectorBSFragment.create(item.title, item.list)
                dialog.onSelect {
                    item.setSelected(it.id)
                    tvSelected.text = selectedString(item, this)
                }
                dialog.show(
                    (getAdapter() as FormLinesAdapter).childFragmentManager,
                    "line_selector"
                )
            }
        }
    }

    private fun selectedString(selector: SelectorLine, v: View) =
        if (selector.currentPosition < 0) "Ничего не выбрано" else selector.getSelectedString()
}