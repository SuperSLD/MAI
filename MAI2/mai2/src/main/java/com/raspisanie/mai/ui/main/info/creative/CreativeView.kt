package com.raspisanie.mai.ui.main.info.creative

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.CreativeLocal
import com.raspisanie.mai.models.realm.CanteenLocal

interface CreativeView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(mutableList: MutableList<CreativeLocal>)
}