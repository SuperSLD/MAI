package com.raspisanie.mai.ui.main.info.canteens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.CanteenLocal
import kotlinx.android.synthetic.main.item_canteens.view.*


class CanteensAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<CanteenLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_canteens, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<CanteenLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val canteen = data as CanteenLocal
            with(itemView) {
                tvTitle.text = canteen.name
                tvLocation.text = canteen.location
                tvTime.text = canteen.time
            }
        }
    }
}