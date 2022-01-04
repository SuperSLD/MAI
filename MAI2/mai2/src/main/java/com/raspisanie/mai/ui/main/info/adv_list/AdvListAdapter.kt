package com.raspisanie.mai.ui.main.info.adv_list

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvAddHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingErrorHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderFactory


class AdvListAdapter(
    private val reload:()->Unit,
    private val openLink:(String)->Unit,
    private val addAdv:()->Unit
) : DiffAdapter() {

    companion object {
        const val ADD_ADV_ITEM = 1001
        const val ADV_ITEM = 1002
        const val LOADING_ITEM = 1003
        const val ERROR_LOADING_ITEM = 1004

        const val ADD_ADV_EVENT = 2001
        const val OPEN_LINK_EVENT = 2002
        const val RELOAD_EVENT = 2003
    }

    private var hasMore = true
    private var errorLoading = false

    override fun initFactory() = HolderFactory(hashMapOf(
                ADD_ADV_ITEM to AdvAddHolder::class.java,
                LOADING_ITEM to AdvLoadingHolder::class.java,
                ERROR_LOADING_ITEM to AdvLoadingErrorHolder::class.java,
                ADV_ITEM to AdvHolder::class.java
            )
        ).onEvent { id, data ->
            when(id) {
                RELOAD_EVENT -> reload()
                OPEN_LINK_EVENT -> openLink(data as String)
                ADD_ADV_EVENT -> addAdv()
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
        getList().addAll(data.map { Pair(ADV_ITEM, it) })
        notifyDataSetChanged()
    }

    fun toggleError(error: Boolean) {
        this.errorLoading = error
        notifyItemChanged(itemCount - 1)
    }
}