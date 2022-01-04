package com.raspisanie.mai.ui.view.form

import androidx.fragment.app.FragmentManager
import com.raspisanie.mai.ui.view.form.holders.*
import com.raspisanie.mai.ui.view.form.lines.FormLine
import online.jutter.diffadapter2.DiffAdapter
import online.jutter.diffadapter2.base.HolderFactory


class FormLinesAdapter(
    val childFragmentManager: FragmentManager
): DiffAdapter() {

    companion object {
        const val TEXT_ITEM = 0
        const val TEXT_INPUT_ITEM = 1
        const val CHECKBOX_ITEM = 2
        const val FILE_ITEM = 3
        const val SELECTOR_ITEM = 4
        const val RADIO_ITEM = 5
    }

    override fun initFactory() = HolderFactory(hashMapOf(
                TEXT_ITEM to TextLineHolder::class.java,
                FILE_ITEM to FileLineHolder::class.java,
                TEXT_INPUT_ITEM to TextInputLineHolder::class.java,
                SELECTOR_ITEM to SelectorLineHolder::class.java,
                CHECKBOX_ITEM to CheckBoxLineHolder::class.java,
                RADIO_ITEM to RadioLineHolder::class.java
            )
        )

    var fileLoader: FileLoader? = null

    fun addData(data: MutableList<FormLine>) {
        val list = getList()
        list.clear()
        data.forEach {
            list.add(Pair(it.getType(), it))
        }
        notifyDataSetChanged()
    }
}