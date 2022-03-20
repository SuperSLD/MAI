package com.raspisanie.mai.domain.controllers

import online.jutter.supersld.common.datacontrol.PublishDataController

class BottomVisibilityController {
    val state = PublishDataController<Boolean>()

    fun show() = state.emit(true)
    fun hide() = state.emit(false)
}
