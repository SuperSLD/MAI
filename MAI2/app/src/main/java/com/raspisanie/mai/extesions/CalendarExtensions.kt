package com.raspisanie.mai.extesions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Парсинг календаря из строки.
 * Полезная функция, везде где идет получение календаря.
 * @param format формат даты ("dd.MM.yyyy")
 */
fun String.parseCalendarByFormat(format: String): Calendar {
        val calendar = Calendar.getInstance()
        val sdf =
                SimpleDateFormat(format, Locale.ENGLISH)
        calendar.time = sdf.parse(this)
    
        return calendar
}