package com.raspisanie.mai.ui.main.info.adv_list.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.domain.models.AdvLocal
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter.Companion.OPEN_LINK_EVENT
import kotlinx.android.synthetic.main.item_adv_holder.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_adv_holder)
class AdvHolder(itemView: View) : DFBaseHolder<AdvLocal>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(item: AdvLocal) {
        with(itemView) {
            tvNameShort.text = "${item.name.first()}${item.lastname.first()}"
            tvName.text = "${item.name} ${item.lastname}"
            tvDate.text = item.createdAt.split(".")[0].fromFormatToFormat("yyyy-MM-dd HH:mm:ss", "HH:mm dd.MM.yyyy")
            tvText.text = item.text

            with(icVk) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, item.vk) }
                if (item.vk.isEmpty()) visibility = View.GONE
            }
            with(icTg) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, item.tg) }
                if (item.tg.isEmpty()) visibility = View.GONE
            }
            with(icOther) {
                setOnClickListener { makeEvent(OPEN_LINK_EVENT, item.other) }
                if (item.other.isEmpty()) visibility = View.GONE
            }
        }
    }
}