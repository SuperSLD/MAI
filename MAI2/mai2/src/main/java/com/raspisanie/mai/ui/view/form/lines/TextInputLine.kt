package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import android.text.InputType
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Ввод текста.
 */
@Parcelize
data class TextInputLine(
    val id: String,
    val name: String,
    val hint: String,
    var value: String = "",
    val inputType: Int = InputType.TYPE_CLASS_TEXT,
    val mandatory: Boolean = false,
    val minLines: Int = 1
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.TEXT_INPUT_ITEM

    override fun valid(): Boolean {
        return !mandatory || (mandatory && value.isNotEmpty())
    }
}