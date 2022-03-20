package com.raspisanie.mai.ui.main.info.library

import com.raspisanie.mai.domain.models.LibraryLocal
import com.raspisanie.mai.ui.main.info.library.holders.LibraryCorpusHolder
import com.raspisanie.mai.ui.main.info.library.holders.LibrarySectionHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.HolderFactory


class LibraryAdapter : DiffAdapter() {

    companion object {
        const val CORPUS_ITEM = 0
        const val SECTION_ITEM = 1
    }

    override fun initFactory() =
        HolderFactory(hashMapOf(
                CORPUS_ITEM to LibraryCorpusHolder::class.java,
                SECTION_ITEM to LibrarySectionHolder::class.java
            )
        )

    fun addData(data: MutableList<LibraryLocal>) {
        val list = getList()
        list.clear()
        for (corpus in data) {
            list.add(Pair(CORPUS_ITEM, corpus))
            for (section in corpus.sections) {
                list.add(Pair(SECTION_ITEM, section))
            }
        }
        notifyDataSetChanged()
    }
}