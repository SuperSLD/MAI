package com.raspisanie.mai.domain.controllers

import online.jutter.supersld.common.datacontrol.PublishDataController

class SelectDateController {

    val state = PublishDataController<String>()
    fun select(date: String) = state.emit(date)
}