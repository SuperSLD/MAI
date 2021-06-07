package com.raspisanie.mai.ui.main.info.sport.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.SportSectionLocal
import com.raspisanie.mai.ui.main.info.sport.SportAdapter
import kotlinx.android.synthetic.main.item_sport_section.view.*
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_sport_section)
class SportSectionHolder(itemView: View) : DFBaseHolder(itemView) {
    override fun bind(data: Any?) {
        val section = data as SportSectionLocal
        with(itemView) {
            tvName.text = section.name
            tvContactName.text = section.contactName
            tvContact.text = section.contact

            btnPhone.setOnClickListener {
                makeEvent(SportAdapter.PHONE_ACTION, section.contact)
            }
        }
    }
}