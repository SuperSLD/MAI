package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Чекбокс
 */
@Parcelize
data class CheckBoxLine(
    val id: String,
    var checked: Boolean = false,
    val text: String,
    val mandatory: Boolean = false
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.CHECKBOX_ITEM

    override fun valid(): Boolean {
        return (mandatory && checked) || !mandatory
    }
}