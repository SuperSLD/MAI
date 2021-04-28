package com.raspisanie.mai.models.local

import android.os.Parcelable
import com.raspisanie.mai.extesions.parseCalendarByFormat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class WeekLocal(
        var id: String,
        var number: Int,
        var date: String,
        var days: MutableList<DayLocal>
): Parcelable {
    /**
     * поиск текущего дня недели, если он
     * отсутствует то возвращается минимальная дата спереди.
     */
    fun getCurrentDay(): DayLocal? {
        var minDate = Int.MAX_VALUE
        var minIndex = -1
        val current = Calendar.getInstance()
        for (i in days.indices) {
            val date = days[i].date.parseCalendarByFormat("dd.MM.yyyy")
            if (date.get(Calendar.DAY_OF_YEAR) >= current.get(Calendar.DAY_OF_YEAR)
                    && date.get(Calendar.DAY_OF_YEAR) - current.get(Calendar.DAY_OF_YEAR) < minDate) {
                minDate = date.get(Calendar.DAY_OF_YEAR) - current.get(Calendar.DAY_OF_YEAR)
                minIndex = i
            }
        }
        return if (minIndex > 0) days[minIndex] else null
    }
}