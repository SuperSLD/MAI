package com.raspisanie.mai.ui.main.info.roadmap.selectroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.extesions.levenshtein
import kotlinx.android.synthetic.main.item_room.view.*
import online.jutter.roadmapview.data.models.map.RMRoom

class RoomsAdapter(
    val roomClick: (RMRoom)->Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = listOf<RMRoom>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setSearchFilter(search: String) {
        this.list = list.sortedBy { levenshtein(it.name, search) }
        notifyDataSetChanged()
    }

    fun addData(rooms: List<RMRoom>) {
        this.list = rooms
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(room: RMRoom) {
            with(itemView) {
                tvTitle.text = room.name
                val addressName = room.structName + (if (room.structName!!.isNotEmpty()) ", " else "") + room.address
                if (addressName.length < 3) {
                    tvNameAddress.visibility = View.GONE
                } else {
                    tvNameAddress.text = addressName
                    tvNameAddress.visibility = View.VISIBLE
                }
                mainCard.setOnClickListener {
                    roomClick(room)
                }
            }
        }
    }
}