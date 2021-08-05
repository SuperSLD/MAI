package com.raspisanie.mai.ui.main.info.search_lector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.TeacherLocal
import kotlinx.android.synthetic.main.item_timetable_subject.view.tvName


class LectorsAdapter(
        private val select: (TeacherLocal) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<TeacherLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_lector, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<TeacherLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val teacher = data as TeacherLocal
            with(itemView) {
                tvName.text = teacher.name

                setOnClickListener {
                    select(teacher)
                }
            }
        }
    }
}