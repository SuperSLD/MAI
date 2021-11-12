package com.raspisanie.mai.ui.view.form

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.raspisanie.mai.ui.view.form.lines.FormLine

@Parcelize
data class FormPage(
    val lines: MutableList<FormLine>,
    val buttonText: String,
    val endAction: String? = null
) : Parcelable {
    companion object {
        const val LAST_PAGE = -1
    }

    fun isValid(): Boolean {
        var isValid = true
        lines.forEach {
            if (!it.valid()) isValid = false
        }
        return isValid
    }
}