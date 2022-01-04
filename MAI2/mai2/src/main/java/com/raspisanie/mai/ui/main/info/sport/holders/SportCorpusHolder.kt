package com.raspisanie.mai.ui.main.info.sport.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.SportLocal
import kotlinx.android.synthetic.main.item_sport_title.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_sport_title)
class SportCorpusHolder(itemView: View) : DFBaseHolder<SportLocal>(itemView) {
    override fun bind(item: SportLocal) {
        with(itemView) {
            tvName.text = item.name
            tvAddress.text = item.address
        }
    }
}