package com.raspisanie.mai.ui.main.info.adv_list.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.models.local.AdvLocal
import com.raspisanie.mai.models.local.LibraryLocal
import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter.Companion.OPEN_LINK_EVENT
import kotlinx.android.synthetic.main.item_adv_error_loading_holder.view.*
import kotlinx.android.synthetic.main.item_adv_holder.view.*
import kotlinx.android.synthetic.main.item_info.view.*
import kotlinx.android.synthetic.main.item_library_section.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.*
import kotlinx.android.synthetic.main.item_sport_title.view.tvName
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_holder)
class AdvHolder(itemView: View) : DFBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val adv = data as AdvLocal
        with(itemView) {
            tvNameShort.text = "${adv.name.first()}${adv.lastname.first()}"
            tvName.text = "${adv.name} ${adv.lastname}"
            tvDate.text = adv.createdAt.split(".")[0].fromFormatToFormat("yyyy-MM-dd HH:mm:ss", "HH:mm dd.MM.yyyy")
            tvText.text = adv.text

            with(icVk) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, adv.vk) }
                if (adv.vk.isEmpty()) visibility = View.GONE
            }
            with(icTg) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, adv.tg) }
                if (adv.tg.isEmpty()) visibility = View.GONE
            }
            with(icOther) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, adv.other) }
                if (adv.other.isEmpty()) visibility = View.GONE
            }
        }
    }
}