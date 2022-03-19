package com.raspisanie.mai.ui.main.info.creative

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.domain.models.CreativeLocal

interface CreativeView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(mutableList: MutableList<CreativeLocal>)
}