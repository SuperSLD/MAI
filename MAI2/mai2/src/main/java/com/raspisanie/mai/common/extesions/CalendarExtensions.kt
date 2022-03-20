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
 * @param from формат даты в текущей строке.
 * @param to формат ожидаемой даты в строке.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
@SuppressLint("SimpleDateFormat")
fun String.fromFormatToFormat(from: String, to: String): String {
        val calendar = this.parseCalendarByFormat(from)
        return SimpleDateFormat(to).format(calendar.time)
}

/**
 * Перевод календаря в строку с определенным форматом.
 *
 * @param format формат ожидаемой даты в строке.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
@SuppressLint("SimpleDateFormat")
fun Calendar.toFormat(format: String): String {
        return SimpleDateFormat(format).format(this.time)
}

/**
 * Устанавливает время в календаре на начало дня.
 */
fun Calendar.setDayStart(): Calendar {
        this.set(Calendar.HOUR_OF_DAY, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MILLISECOND, 0)
        return this
}

/**
 * Устанавливает время в календаре на конец дня.
 */
fun Calendar.setDayEnd(): Calendar {
        this.set(Calendar.HOUR_OF_DAY, 23)
        this.set(Calendar.MINUTE, 59)
        this.set(Calendar.SECOND, 59)
        this.set(Calendar.MILLISECOND, 999)
        return this
}