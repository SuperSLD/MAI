@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.raspisanie.mai.extesions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Парсинг календаря из строки.
 * Полезная функция, везде где идет получение календаря.
 *
 * @param format формат даты ("dd.MM.yyyy")
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
fun String.parseCalendarByFormat(format: String): Calendar {
        val calendar = Calendar.getInstance()
        val sdf =
                SimpleDateFormat(format, Locale.ENGLISH)
        calendar.time = sdf.parse(this)
    
        return calendar
}

/**
 * Перевод строки с датой из одного формата в другой.
 *
 * @param from вормат даты в текущей строке.
 * @param to вормат ожидаемой даты в строке.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
@SuppressLint("SimpleDateFormat")
fun String.fromFormatToFormat(from: String, to: String): String {
        val calendar = this.parseCalendarByFormat(from)
        return SimpleDateFormat(to).format(calendar.time)
}