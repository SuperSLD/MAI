package com.raspisanie.mai.domain.controllers

import online.jutter.supersld.common.datacontrol.PublishDataController

class ConfirmController {

    val state = PublishDataController<Boolean>()
    fun confirm(boolean: Boolean) = state.emit(boolean)
}