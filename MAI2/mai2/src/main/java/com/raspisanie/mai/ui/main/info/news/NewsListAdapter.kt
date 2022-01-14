package com.raspisanie.mai.ui.main.info.news

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingErrorHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingHolder
import com.raspisanie.mai.ui.main.info.news.holders.NewsHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostInfoHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostInfoHolder.Companion.LIKE_CLICK_EVENT
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingErrorHolder.Companion.RELOAD_EVENT
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderFactory


class NewsListAdapter(
    private val reload:()->Unit,
    private val like: (String)->Unit,
) : DiffAdapter() {

    companion object {
        const val NEWS_ITEM = 1002
        const val LOADING_ITEM = 1003
        const val ERROR_LOADING_ITEM = 1004
        const val NEWS_INFO_ITEM = 1005
    }

    private var hasMore = true
    private var errorLoading = false

    override fun initFactory() = HolderFactory(hashMapOf(
                LOADING_ITEM to PostLoadingHolder::class.java,
                ERROR_LOADING_ITEM to PostLoadingErrorHolder::class.java,
                NEWS_ITEM to NewsHolder::class.java,
                NEWS_INFO_ITEM to PostInfoHolder::class.java,
            )
        ).onEvent { id, data ->
            when(id) {
                RELOAD_EVENT -> reload()
                LIKE_CLICK_EVENT -> like(data as String)
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
        val list = getList()
        data.forEach { news ->
            list.add(Pair(NEWS_ITEM, news))
            list.add(Pair(NEWS_INFO_ITEM, news))
        }
        notifyDataSetChanged()
    }

    fun toggleError(error: Boolean) {
        this.errorLoading = error
        notifyItemChanged(itemCount - 1)
    }

    fun updateLike(id: String) {
        val list = getList()
        list.forEachIndexed { index, item ->
            if (item.first == NEWS_INFO_ITEM) {
                val news = item.second as NewsLocal
                if (news.id == id) {
                    news.likeCount++
                    news.isLike = true
                    notifyItemChanged(index)
                }
            }
        }
    }
}