package com.raspisanie.mai.domain.controllers

import online.jutter.supersld.common.datacontrol.PublishDataController

class PointTypeController {
    val state = PublishDataController<PointType>()

    fun select(pointType: PointType) = state.emit(pointType)
}

enum class PointType {
    ROOM,
    MAP,
}