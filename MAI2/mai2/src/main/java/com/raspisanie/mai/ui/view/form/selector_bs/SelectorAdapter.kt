package com.raspisanie.mai.ui.view.form.selector_bs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.lines.SelectorItem
import kotlinx.android.synthetic.main.item_line_selector_item.view.*

class SelectorAdapter(
    val selectItem: (SelectorItem)->Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = mutableListOf<SelectorItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_line_selector_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<SelectorItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SelectorItem) {
            with(itemView) {
                tvName.text = item.text

                setOnClickListener {
                    selectItem(item)
                }
            }
        }
    }
}