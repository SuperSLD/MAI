package com.raspisanie.mai.ui.main.info.adv_list

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.models.realm.DayRealm
import com.raspisanie.mai.models.realm.WeekRealm
import com.raspisanie.mai.models.server.LibraryResponse
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvAddHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingErrorHolder
import com.raspisanie.mai.ui.main.info.adv_list.holders.AdvLoadingHolder
import com.raspisanie.mai.ui.main.info.library.holders.LibraryCorpusHolder
import com.raspisanie.mai.ui.main.info.library.holders.LibrarySectionHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportCorpusHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportSectionHolder
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory


class AdvListAdapter(
    private val reload:()->Unit,
    private val openLink:(String)->Unit,
    private val addAdv:()->Unit
) : DifAdapter() {

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

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
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
    }

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()
    fun hasMore() = hasMore && !errorLoading
    init {
        list.add(Pair(ADD_ADV_ITEM, null))
    }

    override fun onBindViewHolder(holder: DFBaseHolder, position: Int) {
        holder.bind(if (list.size > position) list[position].second else null)
    }

    override fun getItemCount(): Int = if (hasMore) list.size + 1 else list.size

    override fun getItemViewType(position: Int): Int {
        return if (position < list.size) {
            list[position].first
        } else {
            if (errorLoading) ERROR_LOADING_ITEM else LOADING_ITEM
        }
    }

    fun listSize() : Int {
        var count = 0
        list.forEach {
            if (it.first == ADV_ITEM) count++
        }
        return count
    }

    fun addData(data: MutableList<AdvLocal>, hasMore: Boolean) {
        this.hasMore = hasMore
        list.addAll(data.map { Pair(ADV_ITEM, it) })
        notifyDataSetChanged()
    }

    fun toggleError(error: Boolean) {
        this.errorLoading = error
        notifyItemChanged(itemCount - 1)
    }
}