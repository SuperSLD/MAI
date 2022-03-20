package com.raspisanie.mai.ui.main.info.sport.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.SportSectionLocal
import com.raspisanie.mai.ui.main.info.sport.SportAdapter
import kotlinx.android.synthetic.main.item_sport_section.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_sport_section)
class SportSectionHolder(itemView: View) : DFBaseHolder<SportSectionLocal>(itemView) {
    override fun bind(item: SportSectionLocal) {
        with(itemView) {
            tvName.text = item.name
            tvContactName.text = item.contactName
            tvContact.text = item.contact

            btnPhone.setOnClickListener {
                makeEvent(SportAdapter.PHONE_ACTION, item.contact)
            }
        }
    }
}