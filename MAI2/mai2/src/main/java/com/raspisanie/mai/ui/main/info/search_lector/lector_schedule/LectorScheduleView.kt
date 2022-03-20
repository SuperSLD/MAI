package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.domain.models.DayLocal

interface LectorScheduleView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSchedule(day: DayLocal)
}