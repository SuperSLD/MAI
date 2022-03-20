package com.raspisanie.mai.ui.main.info.adv_list

import com.raspisanie.mai.domain.models.AdvLocal
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvAddHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostInfoHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostInfoHolder.Companion.LIKE_CLICK_EVENT
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingErrorHolder
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingErrorHolder.Companion.RELOAD_EVENT
import com.raspisanie.mai.ui.main.info.reused.holders.PostLoadingHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderFactory


class AdvListAdapter(
    private val reload:()->Unit,
    private val openLink:(String)->Unit,
    private val addAdv:()->Unit,
    private val like: (String)->Unit,
) : DiffAdapter() {

    companion object {
        const val ADD_ADV_ITEM = 1001
        const val ADV_ITEM = 1002
        const val LOADING_ITEM = 1003
        const val ERROR_LOADING_ITEM = 1004
        const val ADV_INFO_ITEM = 1005

        const val ADD_ADV_EVENT = 2001
        const val OPEN_LINK_EVENT = 2002
    }

    private var hasMore = true
    private var errorLoading = false

    override fun initFactory() = HolderFactory(hashMapOf(
                ADD_ADV_ITEM to AdvAddHolder::class.java,
                LOADING_ITEM to PostLoadingHolder::class.java,
                ERROR_LOADING_ITEM to PostLoadingErrorHolder::class.java,
                ADV_ITEM to AdvHolder::class.java,
                ADV_INFO_ITEM to PostInfoHolder::class.java
            )
        ).onEvent { id, data ->
            when(id) {
                RELOAD_EVENT -> reload()
                OPEN_LINK_EVENT -> openLink(data as String)
                ADD_ADV_EVENT -> addAdv()
                LIKE_CLICK_EVENT -> like(data as String)
            }
        }


    fun hasMore() = hasMore && !errorLoading
    init {
        getList().add(Pair(ADD_ADV_ITEM, 0))
    }

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
            if (it.first == ADV_ITEM) count++
        }
        return count
    }

    fun addData(data: MutableList<AdvLocal>, hasMore: Boolean) {
        this.hasMore = hasMore
        val list = getList()
        data.forEach { adv ->
            list.add(Pair(ADV_ITEM, adv))
            list.add(Pair(ADV_INFO_ITEM, adv))
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
            if (item.first == ADV_INFO_ITEM) {
                val adv = item.second as AdvLocal
                if (adv.id == id) {
                    adv.likeCount++
                    adv.isLike = true
                    notifyItemChanged(index)
                }
            }
        }
    }
}