package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Текстовое поле формы.
 *  Просто отображение текста и ничего больше.
 */
@Parcelize
data class TextLine(
    val text: String
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.TEXT_ITEM

    override fun valid() = true
}