package com.raspisanie.mai.models.local

import android.os.Parcelable
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.extesions.setDayEnd
import com.raspisanie.mai.extesions.setDayStart
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ScheduleLocal(
        var groupId: String,
        var weeks: MutableList<WeekLocal>
): Parcelable {
    /**
     * Находим текущую неделю.
     */
    fun getCurrentWeek() : WeekLocal? {
        //08.02.2021 - 14.02.2021
        val currentDate = Calendar.getInstance()
        weeks.forEach {
            val splitDate = it.date.split(" - ")
            val timeStart = splitDate[0].parseCalendarByFormat("dd.MM.yyyy")
            val timeEnd = splitDate[1].parseCalendarByFormat("dd.MM.yyyy")
            timeStart.setDayStart()
            timeEnd.setDayEnd()
            if (currentDate.after(timeStart) && currentDate.before(timeEnd)) {
                return it
            }
        }
        return null
    }

    /**
     * Находим следуюшую неделю.
     */
    fun getNextWeek(): WeekLocal? {
        val current = getCurrentWeek()
        return if (current != null) weeks.find { week -> week.number == current.number + 1 }
        else null
    }

    /**
     * Находим предыдущую неделю.
     */
    fun getPreviousWeek(): WeekLocal? {
        val current = getCurrentWeek()
        return if (current != null) weeks.find { week -> week.number == current.number - 1 }
        else null
    }

    /**
     * Просто поиск недели по номеру.
     */
    fun getWeek(number: Int): WeekLocal? {
        return weeks.find { it.number == number }
    }

    /**
     * Поиск экзаменов и создание объекта
     * [WeekLocal] с днями в которых есть экзамены.
     *
     * @return список дней с экзаменами.
     */
    fun extractExams(): WeekLocal? {
        val newWeek = WeekLocal(
                id = "exams",
                number = 0,
                date = "00.00.0000 - 00.00.0000",
                days = mutableListOf()
        )
        weeks.forEach {
            for (day in it.days) {
                var correct = false
                for (subject in day.subjects) {
                    if (subject.type == SubjectLocal.EXAM) correct = true
                }
                if (correct) newWeek.days.add(day)
            }
        }
        return if (newWeek.days.size > 0) newWeek else null
    }
}
