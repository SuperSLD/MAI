package com.raspisanie.mai.ui.main.info.students

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.models.local.StudentOrganizationLocal
import com.raspisanie.mai.models.realm.CanteenLocal

interface StudentsView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(mutableList: MutableList<StudentOrganizationLocal>)
}