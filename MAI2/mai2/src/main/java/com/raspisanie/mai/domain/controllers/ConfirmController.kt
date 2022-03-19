package com.raspisanie.mai.domain.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable

class ConfirmController {
    private val stateRelay = PublishRelay.create<Boolean>()

    val state: Observable<Boolean> = stateRelay
    fun confirm(boolean: Boolean) = stateRelay.accept(boolean)
}