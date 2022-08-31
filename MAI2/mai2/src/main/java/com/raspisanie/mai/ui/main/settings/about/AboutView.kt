package com.raspisanie.mai.ui.main.settings.about

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.domain.models.DevLocal

interface AboutView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDevList(list: MutableList<DevLocal>)

    fun toggleLoading(show: Boolean)
}