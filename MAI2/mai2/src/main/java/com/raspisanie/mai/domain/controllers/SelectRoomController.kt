package com.raspisanie.mai.domain.controllers

import online.jutter.roadmapview.data.models.map.RMRoom
import online.jutter.supersld.common.datacontrol.PublishDataController


class SelectRoomController {
    val state = PublishDataController<RMRoom>()

    fun select(room: RMRoom) = state.emit(room)
}