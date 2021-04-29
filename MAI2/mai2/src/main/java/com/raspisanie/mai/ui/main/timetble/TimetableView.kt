package com.raspisanie.mai.ui.main.timetble

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleTagStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.WeekLocal

interface TimetableView: BaseView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun selectDay(date: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun shoWeek(week: WeekLocal?, number: Int)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun showTitle(week: Int)
}