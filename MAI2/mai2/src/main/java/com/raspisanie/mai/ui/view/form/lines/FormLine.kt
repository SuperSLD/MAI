package com.raspisanie.mai.ui.view.form.lines

import android.os.Parcelable

/**
 * Дефолтный элемент формы.
 */
interface FormLine: Parcelable {
    fun getType(): Int
    fun valid(): Boolean
}