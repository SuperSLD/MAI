package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Выбор из нескольких элементов.
 */
@Parcelize
data class RadioLine(
    val title: String,
    var selectedItem: String? = null,
    val list: MutableList<RadioItem>,
    val mandatory: Boolean = false
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.RADIO_ITEM

    override fun valid(): Boolean {
        return (mandatory && (selectedItem != null)) || !mandatory
    }

    fun setSelected(hash: Int) {
        list.forEach {
            if (it.id.hashCode() == hash) {
                selectedItem = it.id
                return@forEach
            }
        }
    }
}

@Parcelize
data class RadioItem(
    val id: String,
    val text: String
) : Parcelable