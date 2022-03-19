package com.raspisanie.mai.ui.main.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.domain.models.ScheduleLocal
import com.raspisanie.mai.data.db.models.GroupRealm

interface SettingsView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCurrentGroup(group: GroupRealm?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showGroups(groups: MutableList<GroupRealm>)

    @StateStrategyType(SkipStrategy::class)
    fun removeGroup(group: GroupRealm)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showScheduleInfo(list: MutableList<ScheduleLocal>, groups: MutableList<GroupRealm>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDevList(list: MutableList<DevLocal>)

    fun toggleLoading(show: Boolean)
}