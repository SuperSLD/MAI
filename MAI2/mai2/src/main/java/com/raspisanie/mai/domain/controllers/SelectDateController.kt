package com.raspisanie.mai.domain.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable

class SelectDateController {
    private val stateRelay = PublishRelay.create<String>()

    val state: Observable<String> = stateRelay
    fun select(date: String) = stateRelay.accept(date)
}