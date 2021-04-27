package com.raspisanie.mai.ui.select_group.select_group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.realm.GroupRealm
import kotlinx.android.synthetic.main.item_canteens.view.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.tvName


class GroupsAdapter(
        private val select: (GroupRealm) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<GroupRealm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<GroupRealm>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val group = data as GroupRealm
            with(itemView) {
                tvName.text = group.name
                tvFuck.text = group.fac
                tvLevel.text = context.getString(R.string.select_group_level, group.course)

                setOnClickListener {
                    select(group)
                }
            }
        }
    }
}