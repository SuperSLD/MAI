package com.raspisanie.mai.ui.main.info.library.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.LibrarySectionLocal
import com.raspisanie.mai.models.local.SportSectionLocal
import com.raspisanie.mai.ui.main.info.sport.SportAdapter
import kotlinx.android.synthetic.main.item_library_section.view.*
import kotlinx.android.synthetic.main.item_sport_section.view.*
import kotlinx.android.synthetic.main.item_sport_section.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_library_section)
class LibrarySectionHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val section = data as LibrarySectionLocal
        with(itemView) {
            tvName.text = section.name
            tvLocation.text = section.location
        }
    }
}