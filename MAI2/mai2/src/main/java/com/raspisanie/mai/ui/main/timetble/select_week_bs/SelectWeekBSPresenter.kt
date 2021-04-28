package com.raspisanie.mai.ui.main.timetble.select_week_bs

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.SelectWeekController
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class SelectWeekBSPresenter : BasePresenter<MvpView>() {
    private val selectWeekController: SelectWeekController by inject()

    fun select(week: Int) = selectWeekController.select(week)
}