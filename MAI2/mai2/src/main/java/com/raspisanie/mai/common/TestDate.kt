package com.raspisanie.mai.common

import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.models.local.ScheduleLocal
import java.util.*

/**
 * Тестовые даты для проверки текущего семестра.
 * (Первая дата содержит середину первого семестра и
 * если расписание включает в себя эту дату то
 * сейчас первый семестр.)
 *
 * При смене семестра с второго на первый
 * необхо сбросить расписание и предложить выбрать
 * группу еще раз.
 */
object TestDate {

    /** Pair(день, месяц) */
    val FIRST_SEMESTER = Pair(17, 10)
    val SECOND_SEMESTER = Pair(15, 3)

    /** Первый или второй семестр */
    const val FIRST = 1
    const val SECOND = 2

    /**
     * Получение тестовой даты
     * для первого семестра с учетом
     * текущего года.
     */
    fun getFirstDate() = Calendar.getInstance().apply {
        set(Calendar.MONTH, FIRST_SEMESTER.second)
        set(Calendar.DAY_OF_MONTH, FIRST_SEMESTER.first)
    }

    /**
     * Получение тестовой даты
     * для второго семестра с учетом
     * текущего года.
     */
    fun getSecondDate() = Calendar.getInstance().apply {
        set(Calendar.MONTH, SECOND_SEMESTER.second)
        set(Calendar.DAY_OF_MONTH, SECOND_SEMESTER.first)
    }

    /**
     * Получение номера семестра
     * для расписания.
     *
     * @param schedule расписание и причем не важно какое
     *                 любое расписание на устройстве по логике
     *                 должно принадлежать одному семестру.
     *                 (если это не так то стоит задуматься о
     *                 смысле жизни)
     */
    fun getScheduleSemester(schedule: ScheduleLocal, lastSemester: Int): Int {
        val firstDay = schedule.weeks.first().days.first().date
        val lastDay = schedule.weeks.last().days.last().date

        val firstCalendar = firstDay.parseCalendarByFormat("dd.MM.yyyy")
        val lastCalendar = lastDay.parseCalendarByFormat("dd.MM.yyyy")
        val firstSemester = getFirstDate()
        val secondSemester = getSecondDate()

        if (firstCalendar.before(firstSemester) && lastCalendar.after(firstSemester)) return FIRST
        if (firstCalendar.before(secondSemester) && lastCalendar.after(secondSemester)) return SECOND

        return lastSemester
    }
}