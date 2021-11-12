package com.raspisanie.mai.ui.view.form

import androidx.fragment.app.FragmentManager
import com.raspisanie.mai.ui.view.form.holders.*
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory
import com.raspisanie.mai.ui.view.form.lines.FormLine


class FormLinesAdapter(
    val childFragmentManager: FragmentManager
): DifAdapter() {

    companion object {
        const val TEXT_ITEM = 0
        const val TEXT_INPUT_ITEM = 1
        const val CHECKBOX_ITEM = 2
        const val FILE_ITEM = 3
        const val SELECTOR_ITEM = 4
        const val RADIO_ITEM = 5
    }

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
                TEXT_ITEM to TextLineHolder::class.java,
                FILE_ITEM to FileLineHolder::class.java,
                TEXT_INPUT_ITEM to TextInputLineHolder::class.java,
                SELECTOR_ITEM to SelectorLineHolder::class.java,
                CHECKBOX_ITEM to CheckBoxLineHolder::class.java,
                RADIO_ITEM to RadioLineHolder::class.java
            )
        )
    }

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()
    var fileLoader: FileLoader? = null


    override fun onBindViewHolder(holder: DFBaseHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(data: MutableList<FormLine>) {
        this.list.clear()
        data.forEach {
            this.list.add(Pair(it.getType(), it))
        }
        notifyDataSetChanged()
    }
}