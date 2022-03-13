package com.raspisanie.mai.ui.main.info.reused.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter
import kotlinx.android.synthetic.main.item_adv_error_loading_holder.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_error_loading_holder)
class PostLoadingErrorHolder(itemView: View) : DFBaseHolder<Int>(itemView) {
    companion object {
        const val RELOAD_EVENT = -1001
    }

    override fun bind(item: Int) {
        with(itemView) {
            tvReload.setOnClickListener {
                makeEvent(RELOAD_EVENT)
            }
        }
    }
}