package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Выбор из нескольких элементов.
 */
@Parcelize
data class SelectorLine(
    val id: String,
    val title: String,
    var currentPosition: Int = -1,
    val list: MutableList<SelectorItem>,
    val mandatory: Boolean = false
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.SELECTOR_ITEM

    override fun valid(): Boolean {
        return (mandatory && (currentPosition >= 0)) || !mandatory
    }

    fun setSelected(id: Int) {
        for (i in list.indices) {
            if (id == list[i].id) {
                currentPosition = i
                break
            }
        }
    }

    fun getSelectedString() : String? {
        return if (currentPosition < 0) {
            null
        } else {
            val selectedItem = list[currentPosition]
            "${selectedItem.title} ${selectedItem.text}"
        }
    }

    fun getSelectedId() : Int {
        return list[currentPosition].id
    }
}

@Parcelize
data class SelectorItem(
    val id: Int,
    val title: String,
    val text: String
) : Parcelable