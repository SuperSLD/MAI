package com.raspisanie.mai.ui.main.info.adv_list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.AdvLocal
import com.raspisanie.mai.models.realm.CanteenLocal

interface AdvView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun addList(list: MutableList<AdvLocal>)

    @StateStrategyType(SkipStrategy::class)
    fun updateLike(id: String)
}