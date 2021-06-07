package com.raspisanie.mai.ui.main.info.library

import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.LibraryLocal
import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.models.realm.DayRealm
import com.raspisanie.mai.models.realm.WeekRealm
import com.raspisanie.mai.models.server.LibraryResponse
import com.raspisanie.mai.ui.main.info.library.holders.LibraryCorpusHolder
import com.raspisanie.mai.ui.main.info.library.holders.LibrarySectionHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportCorpusHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportSectionHolder
import com.raspisanie.mai.ui.main.timetble.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory


class LibraryAdapter : DifAdapter() {

    companion object {
        const val CORPUS_ITEM = 0
        const val SECTION_ITEM = 1
    }

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
                CORPUS_ITEM to LibraryCorpusHolder::class.java,
                SECTION_ITEM to LibrarySectionHolder::class.java
            )
        )
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

    fun addData(data: MutableList<LibraryLocal>) {
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