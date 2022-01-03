package com.raspisanie.mai.ui.main.info.adv_list.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_add_holder)
class AdvAddHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        with(itemView) {
            setOnClickListener {
                makeEvent(AdvListAdapter.ADD_ADV_EVENT)
            }
        }
    }
}