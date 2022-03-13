package com.raspisanie.mai.ui.main.info.news

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.NewsLocal
import com.raspisanie.mai.models.realm.CanteenLocal

interface NewsView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(mutableList: MutableList<NewsLocal>)

    @StateStrategyType(SkipStrategy::class)
    fun updateLike(id: String)
}