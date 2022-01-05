package com.raspisanie.mai.ui.main.info.students

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.StudentOrganizationLocal
import kotlinx.android.synthetic.main.item_stud.view.*


class StudentsAdapter(
    private val callPhone:(String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<StudentOrganizationLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_stud, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<StudentOrganizationLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val students = data as StudentOrganizationLocal
            with(itemView) {
                tvName.text = students.name
                tvContact.text = students.contact
                tvAddress.text = students.address

                tvContact.setOnClickListener {
                    callPhone(students.contact)
                }
            }
        }
    }
}