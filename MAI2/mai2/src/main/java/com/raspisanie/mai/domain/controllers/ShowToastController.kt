package com.raspisanie.mai.domain.controllers

import com.raspisanie.mai.common.enums.ToastType
import online.jutter.supersld.common.datacontrol.PublishDataController

class ShowToastController {
    val state = PublishDataController<Pair<ToastType, String>>()

    fun show(toastType: ToastType, message: String = "") =
        state.emit(Pair(toastType, message))
}