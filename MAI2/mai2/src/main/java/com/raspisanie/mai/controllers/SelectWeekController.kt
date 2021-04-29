package com.raspisanie.mai.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SelectWeekController {
    private val stateRelay = PublishRelay.create<Int>()

    val state: Observable<Int> = stateRelay
    fun select(week: Int) = stateRelay.accept(week)

    /**
     * Особые номера недель, чтобы
     * открывать нужную.
     *
     * Исключение [OTHER_WEEK]. Если выбран этот номер,
     * то расписание не меняет свою неделю, но предлагает
     * выбрать неделю из списка.
     */
    companion object {
        const val THIS_WEEK = -1
        const val PREVIOUS_WEEK = -2
        const val NEXT_WEEK = -3

        const val OTHER_WEEK = -100
    }
}