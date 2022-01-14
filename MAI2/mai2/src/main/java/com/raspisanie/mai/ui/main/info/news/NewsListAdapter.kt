package com.raspisanie.mai.ui.main.info.news

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvAddHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingErrorHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingHolder
import com.raspisanie.mai.ui.main.info.news.holders.NewsHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderFactory


class NewsListAdapter(
    private val reload:()->Unit,
) : DiffAdapter() {

    companion object {
        const val NEWS_ITEM = 1002
        const val LOADING_ITEM = 1003
        const val ERROR_LOADING_ITEM = 1004

        const val RELOAD_EVENT = 2003
    }

    private var hasMore = true
    private var errorLoading = false

    override fun initFactory() = HolderFactory(hashMapOf(
                LOADING_ITEM to AdvLoadingHolder::class.java,
                ERROR_LOADING_ITEM to AdvLoadingErrorHolder::class.java,
                NEWS_ITEM to NewsHolder::class.java,
            )
        ).onEvent { id, data ->
            when(id) {
                RELOAD_EVENT -> reload()
            }
        }


    fun hasMore() = hasMore && !errorLoading

    override fun onBindViewHolder(holder: DFBaseHolder<Any>, position: Int) {
        holder.pubBind(if (getList().size > position) getList()[position].second else 0)
    }

    override fun getItemCount(): Int = if (hasMore) getList().size + 1 else getList().size

    override fun getItemViewType(position: Int): Int {
        return if (position < getList().size) {
            getList()[position].first
        } else {
            if (errorLoading) ERROR_LOADING_ITEM else LOADING_ITEM
        }
    }

    fun listSize() : Int {
        var count = 0
        getList().forEach {
            if (it.first == NEWS_ITEM) count++
        }
        return count
    }

    fun addData(data: MutableList<NewsLocal>, hasMore: Boolean) {
        this.hasMore = hasMore
        getList().addAll(data.map { Pair(NEWS_ITEM, it) })
        notifyDataSetChanged()
    }

    fun toggleError(error: Boolean) {
        this.errorLoading = error
        notifyItemChanged(itemCount - 1)
    }
}