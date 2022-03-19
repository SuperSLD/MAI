package com.raspisanie.mai.ui.main.exams

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.domain.models.WeekLocal

interface ExamsView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExams(week: WeekLocal?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyGroups()
}