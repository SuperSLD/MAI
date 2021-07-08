package com.raspisanie.mai.ui.main.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.NotificationsLocal
import com.raspisanie.mai.models.realm.CanteenLocal

interface InfoView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showNotifications(notifications: NotificationsLocal)
}