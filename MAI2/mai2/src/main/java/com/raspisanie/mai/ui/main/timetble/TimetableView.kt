package com.raspisanie.mai.ui.main.timetble

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.WeekLocal

interface TimetableView: BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun selectDay(date: String)

    @StateStrategyType(SkipStrategy::class)
    fun shoWeek(week: WeekLocal?)
}