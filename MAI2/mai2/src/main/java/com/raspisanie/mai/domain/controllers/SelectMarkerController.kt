package com.raspisanie.mai.domain.controllers

import online.jutter.roadmapview.data.models.map.RMMarker
import online.jutter.supersld.common.datacontrol.PublishDataController


class SelectMarkerController {
    val state = PublishDataController<RMMarker>()

    fun select(notifications: RMMarker) = state.emit(notifications)
}