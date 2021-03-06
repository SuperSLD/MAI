package com.raspisanie.mai.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ConfirmController {
    private val stateRelay = PublishRelay.create<Boolean>()

    val state: Observable<Boolean> = stateRelay
    fun confirm(boolean: Boolean) = stateRelay.accept(boolean)
}