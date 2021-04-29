package com.raspisanie.mai.ui.main.timetble.select_number

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.WeekLocal

interface SelectNumberView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(weeks: MutableList<WeekLocal>?)
}