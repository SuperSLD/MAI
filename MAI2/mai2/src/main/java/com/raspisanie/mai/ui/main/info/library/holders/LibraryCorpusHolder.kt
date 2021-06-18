package com.raspisanie.mai.ui.main.info.library.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.LibraryLocal
import com.raspisanie.mai.models.local.SportLocal
import kotlinx.android.synthetic.main.item_library_section.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_library_title)
class LibraryCorpusHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val corpus = data as LibraryLocal
        with(itemView) {
            tvName.text = corpus.name
        }
    }
}