package com.raspisanie.mai.models.local

import android.os.Parcelable
import com.raspisanie.mai.extesions.parseCalendarByFormat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ScheduleLocal(
        var groupId: String,
        var weeks: MutableList<WeekLocal>
): Parcelable {
    fun getCurrentWeek() : WeekLocal? {
        //08.02.2021 - 14.02.2021
        val currentDate = Calendar.getInstance()
        weeks.forEach {
            val splitDate = it.date.split(" - ")
            val timeStart = splitDate[0].parseCalendarByFormat("dd.MM.yyyy")
            val timeEnd = splitDate[1].parseCalendarByFormat("dd.MM.yyyy")
            if (currentDate.after(timeStart) && currentDate.before(timeEnd)) {
                return it
            }
        }
        return null
    }
}
