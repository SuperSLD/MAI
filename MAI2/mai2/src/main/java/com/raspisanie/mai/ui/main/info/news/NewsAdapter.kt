package com.raspisanie.mai.ui.main.info.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.models.local.NewsLocal
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<NewsLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<NewsLocal>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(news: NewsLocal, position: Int) {
            with(itemView) {
                tvTitle.text = "${news.title} "
                tvAuthor.text = news.author
                tvText.text = news.text
                tvDate.text = news.createdAt.split(".")[0].fromFormatToFormat("yyyy-MM-dd HH:mm:ss", "HH:mm dd.MM.yyyy")
                ivWarning.visibility = if (news.warning) View.VISIBLE else View.GONE
                //vDivider.visibility =
                //if ((position < list.lastIndex && list[position + 1].warning) || news.warning) View.INVISIBLE else View.VISIBLE
            }
        }
    }
}