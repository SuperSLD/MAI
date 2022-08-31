package com.raspisanie.mai.ui.main.settings.about

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.DevLocal
import kotlinx.android.synthetic.main.item_dev.view.*

class DevListAdapter(
        private val openLink: (link: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<DevLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_dev, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<DevLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Any?) {
            val dev = data as DevLocal
            with(itemView) {
                tvName.text = dev.name
                val info = dev.from.split("$")
                tvOrganisation.text = info[0]
                if (info.size > 1) tvWork.text = info[1]
                cvDev.setOnClickListener {
                    openLink(dev.link)
                }
            }
        }
    }
}