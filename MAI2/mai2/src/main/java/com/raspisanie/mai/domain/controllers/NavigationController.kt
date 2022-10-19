package com.raspisanie.mai.domain.controllers

import online.jutter.roadmapview.data.models.map.RMMarker


class NavigationController {
    private var start: RMMarker? = null
    private var end: RMMarker? = null

    fun setMarkers(start: RMMarker, end: RMMarker) {
        this.start = start
        this.end = end
    }

    fun clearData() {
        start = null
        end = null
    }

    fun get() = if (start != null && end != null) Pair(start!!, end!!) else null
}