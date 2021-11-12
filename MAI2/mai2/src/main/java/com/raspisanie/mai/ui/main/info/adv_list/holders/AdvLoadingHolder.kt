package com.raspisanie.mai.ui.main.info.adv_list.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.AdvLocal
import com.raspisanie.mai.models.local.LibraryLocal
import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter
import kotlinx.android.synthetic.main.item_library_section.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_loading_holder)
class AdvLoadingHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {}
}