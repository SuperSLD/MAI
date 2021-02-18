package com.raspisanie.mai.ui.main.info.canteens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.AbstractViewHolder
import com.raspisanie.mai.models.human.CanteenHuman
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.WeekHuman
import com.raspisanie.mai.ui.main.timetble.holders.TimetableHolderFactory
import com.raspisanie.mai.ui.main.timetble.holders.TitleHolder
import kotlinx.android.synthetic.main.item_timetable_subject.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlin.time.seconds


class CanteensAdapter : RecyclerView.Adapter<AbstractViewHolder>() {

    private val list = mutableListOf<CanteenHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_canteens, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<CanteenHuman>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : AbstractViewHolder(itemView) {

        override fun bind(data: Any?) {
            val canteen = data as CanteenHuman
            with(itemView) {
                tvTitle.text = canteen.name
                tvLocation.text = canteen.location
                tvTime.text = canteen.time
            }
        }
    }
}