package com.raspisanie.mai.ui.main.info.roadmap.navigation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import online.jutter.roadmapview.data.models.map.RMMarker

interface NavigationView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addStartMarker(marker: RMMarker)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addEndMarker(marker: RMMarker)
}