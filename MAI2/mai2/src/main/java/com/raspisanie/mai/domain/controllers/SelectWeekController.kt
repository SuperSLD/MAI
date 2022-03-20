package com.raspisanie.mai.domain.controllers

import online.jutter.supersld.common.datacontrol.PublishDataController

class SelectWeekController {
    val state = PublishDataController<Int>()
    fun select(week: Int) = state.emit(week)

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