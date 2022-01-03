package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.FormLinesAdapter

/**
 *  Загрузка файла
 */
@Parcelize
data class FileLine(
    val id: String,
    var name: String,
    val mandatory: Boolean = false,
    var fileName: String = "",
    var data: String? = null,
    var loading: Boolean = false,
    var position: Int = 0
) : FormLine, Parcelable {
    override fun getType(): Int = FormLinesAdapter.FILE_ITEM

    override fun valid(): Boolean {
        return !data.isNullOrEmpty() || !mandatory
    }
}