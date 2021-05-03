package com.raspisanie.mai.ui.main.settings

import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.realm.remove
import com.raspisanie.mai.extesions.shortVibration
import com.raspisanie.mai.models.local.ScheduleLocal
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.realm.GroupRealm
import kotlinx.android.synthetic.main.item_canteens.view.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_group_settings.view.*
import kotlinx.android.synthetic.main.item_schedule_settings.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.tvName


class ScheduleListAdapter(
        private val colors: MutableList<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Pair<GroupRealm, ScheduleLocal>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_settings, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<ScheduleLocal>, groups: MutableList<GroupRealm>) {
        this.list.clear()
        list.forEach { sch ->
            this.list.add(Pair(groups.find { sch.groupId == it.id }!!, sch))
        }
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Pair<GroupRealm, ScheduleLocal>, position: Int) {
            with(itemView) {
                tvName.text = data.first.name
                tvSize.text = context.getString(R.string.settings_diagram_kb, data.second.size)
                cvCircle.setCardBackgroundColor(colors[position])
            }
        }

        fun clear() {
            with(itemView) {
                ibDelete.visibility = View.VISIBLE
                btnRadio.isChecked = false
            }
        }
    }
}