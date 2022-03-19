package com.raspisanie.mai.domain.controllers

import com.jakewharton.rxrelay3.PublishRelay
import com.raspisanie.mai.common.enums.ToastType
import io.reactivex.rxjava3.core.Observable

class ShowToastController {
    private val stateRelay = PublishRelay.create<Pair<ToastType, String>>()

    val state: Observable<Pair<ToastType, String>> = stateRelay
    fun show(toastType: ToastType, message: String = "") =
        stateRelay.accept(Pair(toastType, message))
}