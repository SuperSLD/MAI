package com.raspisanie.mai.ui.main.settings

import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.realm.remove
import com.raspisanie.mai.extesions.shortVibration
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.realm.GroupRealm
import kotlinx.android.synthetic.main.item_canteens.view.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_group_settings.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.tvName


class GroupsListAdapter(
        private val select: (GroupRealm) -> Unit,
        private val remove: (GroupRealm) -> Unit,
        private var vibrator: Vibrator
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<GroupRealm>()
    private var lastHolder: ItemHolder? = null
    private var lastGroup: GroupRealm? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_group_settings, parent, false)
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

    fun removeGroup(group: GroupRealm) {
        vibrator.shortVibration()
        val pos = list.indexOf(group)
        list.remove(group)
        notifyItemRemoved(pos)
        group.remove()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Any?) {
            val group = data as GroupRealm
            with(itemView) {
                tvName.text = group.name
                if (group.selected) {
                    btnRadio.isChecked = group.selected
                    ibDelete.visibility = View.INVISIBLE
                    lastGroup = group
                    lastHolder = this@ItemHolder
                } else {
                    ibDelete.visibility = View.VISIBLE
                    btnRadio.isChecked = false
                }

                ibDelete.setOnClickListener {
                    remove(group)
                }

                setOnClickListener {
                    if (!btnRadio.isChecked) {
                        group.realm.executeTransaction {
                            group.selected = true
                        }
                        lastHolder?.clear()
                        lastGroup?.realm?.executeTransaction {
                            lastGroup?.selected = false
                        }
                        lastHolder = this@ItemHolder
                        lastGroup = group
                        btnRadio.isChecked = group.selected
                        ibDelete.visibility = View.INVISIBLE
                        select(group)
                    }
                }
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