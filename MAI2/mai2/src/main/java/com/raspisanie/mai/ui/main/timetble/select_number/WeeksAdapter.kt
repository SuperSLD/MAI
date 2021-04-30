package com.raspisanie.mai.ui.main.timetble.select_number

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.realm.GroupRealm
import kotlinx.android.synthetic.main.item_canteens.view.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import kotlinx.android.synthetic.main.item_timetable_subject.view.tvName
import kotlinx.android.synthetic.main.item_week.view.*


class WeeksAdapter(
        private val select: (Int) -> Unit,
        private val selectedNumber: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<WeekLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_week, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<WeekLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val primary = ContextCompat.getColor(itemView.context, R.color.colorPrimarySecondary)
        private val back = ContextCompat.getColor(itemView.context, R.color.colorBackground)
        private val white = ContextCompat.getColor(itemView.context, R.color.colorTextWhite)
        private val black = ContextCompat.getColor(itemView.context, R.color.colorTextPrimary)
        private val secondary = ContextCompat.getColor(itemView.context, R.color.colorTextSecondary)

        fun bind(data: Any?) {
            val week = data as WeekLocal
            with(itemView) {
                tvName.text = context.getString(R.string.timetable_week_other, week.number)
                tvDate.text = week.date

                if (selectedNumber != week.number) {
                    setOnClickListener {
                        select(week.number)
                    }
                    changeColor(false)
                    ivArrow.visibility = View.VISIBLE
                    vDivider.visibility = View.VISIBLE
                } else {
                    setOnClickListener {  }
                    changeColor(true)
                    ivArrow.visibility = View.GONE
                    vDivider.visibility = View.GONE
                }
            }
        }

        private fun changeColor(selected: Boolean) {
            with(itemView) {
                tvName.setTextColor(if (selected) white else black)
                tvDate.setTextColor(if (selected) white else secondary)
                (this as MaterialCardView).setCardBackgroundColor(if (selected) primary else back)
            }
        }
    }
}