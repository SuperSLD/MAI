package com.raspisanie.mai.ui.main.info.sport.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.SportLocal
import kotlinx.android.synthetic.main.item_sport_title.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_sport_title)
class SportCorpusHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val corpus = data as SportLocal
        with(itemView) {
            tvName.text = corpus.name
            tvAddress.text = corpus.address
        }
    }
}