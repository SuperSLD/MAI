package com.raspisanie.mai.common.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Any?)
}