package com.raspisanie.mai.ui.main.info.search_lector

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.TeacherLocal

interface SearchLectorView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(groups: MutableList<TeacherLocal>)
}