package com.raspisanie.mai.ui.main.info.search_lector

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.domain.models.TeacherLocal

interface SearchLectorView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(groups: MutableList<TeacherLocal>)
}