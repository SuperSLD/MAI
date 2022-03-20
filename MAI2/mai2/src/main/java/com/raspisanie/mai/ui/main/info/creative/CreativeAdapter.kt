package com.raspisanie.mai.ui.main.info.creative

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.CreativeLocal
import kotlinx.android.synthetic.main.item_creative_group.view.*


class CreativeAdapter(
    private val callPhone: (String)->Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<CreativeLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_creative_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<CreativeLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val creative = data as CreativeLocal
            with(itemView) {
                tvName.text = creative.name
                tvContactName.text = creative.contactName
                tvDescription.text = creative.description

                btnCall.setOnClickListener {
                    callPhone(creative.contact)
                }
            }
        }
    }
}