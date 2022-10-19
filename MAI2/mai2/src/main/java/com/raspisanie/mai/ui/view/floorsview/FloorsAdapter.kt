package com.raspisanie.mai.ui.view.floorsview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.item_floor.view.*


class FloorsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Int>()
    private var selectedFloor = 1
    private var lastFloorCount = -1
    private var floorChangeListener = { _:Int->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_floor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateFloors(count: Int) {
        if (lastFloorCount != count) {
            this.list.clear()
            this.list.addAll(1..count)
            this.list.reverse()
            selectedFloor = 1
            lastFloorCount = count
            notifyDataSetChanged()
        }
    }

    fun onFloorChanged(l: (Int)->Unit) {
        floorChangeListener = l
    }

    fun setFloor(floor: Int) {
        selectedFloor = floor
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(floor: Int) {
            with(itemView) {
                setColor(floor == selectedFloor)
                tvFloor.text = floor.toString()
                tvFloor.setOnClickListener {
                    selectedFloor = floor
                    floorChangeListener(floor)
                    notifyDataSetChanged()
                }
            }
        }

        private fun setColor(selected: Boolean) {
            with(itemView) {
                if (selected) {
                    tvFloor.setTextColor(ContextCompat.getColor(context, R.color.colorTextWhite))
                    tvFloor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                } else {
                    tvFloor.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
                    tvFloor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundSecondary))
                }
            }
        }
    }
}