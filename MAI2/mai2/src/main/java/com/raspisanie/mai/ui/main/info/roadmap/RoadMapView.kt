package com.raspisanie.mai.ui.main.info.roadmap

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import online.jutter.roadmapview.data.models.map.RMMarker

interface RoadMapView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun findRoad(markers: Pair<RMMarker, RMMarker>)
}