package com.raspisanie.mai.ui.main.timetble.select_number

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.domain.models.WeekLocal

interface SelectNumberView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(weeks: MutableList<WeekLocal>?)
}