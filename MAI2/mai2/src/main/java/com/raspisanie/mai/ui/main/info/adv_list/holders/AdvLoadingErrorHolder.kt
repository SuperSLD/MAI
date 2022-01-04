package com.raspisanie.mai.ui.main.info.adv_list.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter
import kotlinx.android.synthetic.main.item_adv_error_loading_holder.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_error_loading_holder)
class AdvLoadingErrorHolder(itemView: View) : DFBaseHolder<Int>(itemView) {
    override fun bind(item: Int) {
        with(itemView) {
            tvReload.setOnClickListener {
                makeEvent(AdvListAdapter.RELOAD_EVENT)
            }
        }
    }
}