package com.raspisanie.mai.ui.main.info.library.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.LibraryLocal
import kotlinx.android.synthetic.main.item_sport_title.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_library_title)
class LibraryCorpusHolder(itemView: View) : DFBaseHolder<LibraryLocal>(itemView) {
    override fun bind(item: LibraryLocal) {
        with(itemView) {
            tvName.text = item.name
        }
    }
}