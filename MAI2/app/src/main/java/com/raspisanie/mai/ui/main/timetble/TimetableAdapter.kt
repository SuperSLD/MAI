package com.raspisanie.mai.ui.main.timetble

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.models.human.WeekHuman
import com.raspisanie.mai.ui.main.timetble.holders.TimetableHolderFactory
import timber.log.Timber
import java.util.*


class TimetableAdapter : RecyclerView.Adapter<AbstractViewHolder>() {

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return TimetableHolderFactory.create(viewType, parent, null)!!
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(data: WeekHuman) {
        this.list.clear()
        for (day in data.days) {
            this.list.add(Pair(TimetableHolderFactory.TITLE_ITEM, day))
        }
        notifyDataSetChanged()
    }
}