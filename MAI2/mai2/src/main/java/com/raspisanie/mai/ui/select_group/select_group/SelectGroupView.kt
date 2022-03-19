package com.raspisanie.mai.ui.select_group.select_group

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.common.base.BaseView

interface SelectGroupView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(groups: MutableList<GroupRealm>)
}