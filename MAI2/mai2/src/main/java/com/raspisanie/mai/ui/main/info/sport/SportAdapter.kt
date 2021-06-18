package com.raspisanie.mai.ui.main.info.sport

import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.models.realm.DayRealm
import com.raspisanie.mai.models.realm.WeekRealm
import com.raspisanie.mai.ui.main.info.sport.holders.SportCorpusHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportSectionHolder
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory


class SportAdapter(
        val onItemClick: (Int, Any?) -> Unit
) : DifAdapter() {

    companion object {
        const val CORPUS_ITEM = 0
        const val SECTION_ITEM = 1

        const val PHONE_ACTION = 0
    }

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
                CORPUS_ITEM to SportCorpusHolder::class.java,
                SECTION_ITEM to SportSectionHolder::class.java
            )
        ).onEvent{ id, data ->
            onItemClick(id, data)
        }
    }

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()

    override fun onBindViewHolder(holder: DFBaseHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(data: MutableList<SportLocal>) {
        this.list.clear()
        for (corpus in data) {
            this.list.add(Pair(CORPUS_ITEM, corpus))
            for (section in corpus.sections) {
                this.list.add(Pair(SECTION_ITEM, section))
            }
        }
        notifyDataSetChanged()
    }
}