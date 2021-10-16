package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.TeacherLocal
import com.raspisanie.mai.models.realm.DayRealm

interface LectorScheduleView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSchedule(day: DayLocal)
}