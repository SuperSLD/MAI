package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_form.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.raspisanie.mai.ui.view.form.FormLinesAdapter
import com.raspisanie.mai.ui.view.form.lines.SelectorLine
import com.raspisanie.mai.ui.view.form.selector_bs.SelectorBSFragment
import kotlinx.android.synthetic.main.item_line_selector.view.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_selector)
class SelectorLineHolder(itemView: View) : DFBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val selector = data as SelectorLine
        with(itemView) {
            tvName.text = selector.title + if(selector.mandatory) "*" else ""
            tvSelected.text = selectedString(selector, this)

            cvSelector.setOnClickListener {
                val dialog = SelectorBSFragment.create(selector.title, selector.list)
                dialog.onSelect {
                    selector.setSelected(it.id)
                    tvSelected.text = selectedString(selector, this)
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