package com.raspisanie.mai.ui.main.timetble

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.models.human.DayHuman

interface TimetableView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun selectDay(date: String)
}