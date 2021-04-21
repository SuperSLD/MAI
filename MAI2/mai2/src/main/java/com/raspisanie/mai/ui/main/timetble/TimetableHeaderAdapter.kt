package com.raspisanie.mai.ui.main.timetble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.WeekLocal
import kotlinx.android.synthetic.main.item_timetable_header.view.*
import java.util.*


class TimetableHeaderAdapter(
        var onDayHeaderClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Any>()
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHeaderHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_header, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHeaderHolder) holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addData(data: WeekLocal) {
        this.list.clear()

        for (day in data.days) {
            this.list.add(day)
        }

        notifyDataSetChanged()
    }

    fun selectItem(date: String) {
        for (i in list.indices) {
            val day = list[i] as DayLocal
            if (day.date == date) {
                selectedPosition = i
            }
        }
        notifyDataSetChanged()
    }

    fun getItemPosition(date: String): Int {
        for (i in list.indices) {
            val day = list[i] as DayLocal
            if (day.date == date) {
                return i
            }
        }
        return 0
    }

    private inner class ItemHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayNames = itemView.resources.getStringArray(R.array.timetable_days)

        fun bind(data: Any?) {
            val day = data as DayLocal
            addColor(list.indexOf(day) == selectedPosition)
            with(itemView) {

                val calendar = day.date.parseCalendarByFormat("dd.MM.yyyy")
                tvDayName.text = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]

                cvMain.setOnClickListener {
                    selectedPosition = list.indexOf(day)
                    onDayHeaderClick(day.date)
                    notifyDataSetChanged()
                }
            }
        }

        fun addColor(selected: Boolean) {
            with(itemView) {
                if (selected) {
                    cvMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    cvMain.strokeColor = ContextCompat.getColor(context, R.color.colorPrimary)
                    tvDayName.setTextColor(ContextCompat.getColor(context, R.color.colorTextWhite))
                } else {
                    cvMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorBackground))
                    cvMain.strokeColor = ContextCompat.getColor(context, R.color.colorBorder)
                    tvDayName.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
                }
            }
        }
    }
}