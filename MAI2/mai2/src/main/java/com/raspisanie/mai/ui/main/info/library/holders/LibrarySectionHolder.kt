package com.raspisanie.mai.ui.main.info.library.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.LibrarySectionLocal
import kotlinx.android.synthetic.main.item_library_section.view.*
import kotlinx.android.synthetic.main.item_sport_section.view.tvName
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_library_section)
class LibrarySectionHolder(itemView: View) : DFBaseHolder<LibrarySectionLocal>(itemView) {
    override fun bind(item: LibrarySectionLocal) {
        with(itemView) {
            tvName.text = item.name
            tvLocation.text = item.location
        }
    }
}