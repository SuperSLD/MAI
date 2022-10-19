package com.raspisanie.mai.ui.main.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.domain.models.NotificationsLocal

interface InfoView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showNotifications(notifications: NotificationsLocal)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun enableTestMap()
}