package com.raspisanie.mai.ui.main.info.canteens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.WeekHuman
import com.raspisanie.mai.ui.main.timetble.holders.TimetableHolderFactory
import com.raspisanie.mai.ui.main.timetble.holders.TitleHolder
import kotlin.time.seconds


class CanteensAdapter : RecyclerView.Adapter<AbstractViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_canteens, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(null)
    }

    override fun getItemCount(): Int = 50

    inner class ItemHolder(itemView: View) : AbstractViewHolder(itemView) {

        override fun bind(data: Any?) {

        }
    }
}