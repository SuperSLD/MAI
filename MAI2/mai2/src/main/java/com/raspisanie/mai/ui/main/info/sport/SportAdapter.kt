package com.raspisanie.mai.ui.main.info.sport

import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.ui.main.info.sport.holders.SportCorpusHolder
import com.raspisanie.mai.ui.main.info.sport.holders.SportSectionHolder
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.HolderFactory


class SportAdapter(
        val onItemClick: (Int, Any?) -> Unit
) : DiffAdapter() {

    companion object {
        const val CORPUS_ITEM = 0
        const val SECTION_ITEM = 1

        const val PHONE_ACTION = 0
    }

    override fun initFactory() =
        HolderFactory(hashMapOf(
                CORPUS_ITEM to SportCorpusHolder::class.java,
                SECTION_ITEM to SportSectionHolder::class.java
            )
        ).onEvent{ id, data ->
            onItemClick(id, data)
        }

    fun addData(data: MutableList<SportLocal>) {
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