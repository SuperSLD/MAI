package com.raspisanie.mai.ui.view.form

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Форма для заполнения
 * в каком-то поэтапном действии.
 */
@Parcelize
data class Form(
    val title: FormTitle? = null,
    val name: String? = null,
    val finishText: String = "Завершить",
    val pages: MutableList<FormPage>
): Parcelable {
    /**
     * Проверяем все поля формы на каждой
     * странице на верностьб заполнения.
     * Каждая линия формы сама определяет
     * правильно она заполнена или нет.
     *
     * @return true если форма полностью заполнена правильно.
     */
    fun isValid(): Boolean {
        var isValid = true
        pages.forEach { page ->
            if (!page.isValid()) isValid = false
        }
        return isValid
    }
}

/**
 * Заголовок формы.
 * Отображается как большая красная надпись
 * и небольшое серое описание.
 */
@Parcelize
data class FormTitle(
    val title: String,
    val description: String
) : Parcelable