package com.raspisanie.mai.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SelectWeekController {
    private val stateRelay = PublishRelay.create<Int>()

    val state: Observable<Int> = stateRelay
    fun select(week: Int) = stateRelay.accept(week)

    companion object {
        const val THIS_WEEK = -1
        const val PREVIOUS_WEEK = -2
        const val NEXT_WEEK = -3
    }
}