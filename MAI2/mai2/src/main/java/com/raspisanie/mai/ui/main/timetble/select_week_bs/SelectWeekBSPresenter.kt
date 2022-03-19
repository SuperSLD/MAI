package com.raspisanie.mai.ui.main.timetble.select_week_bs

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.SelectWeekController
import com.yandex.metrica.YandexMetrica
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter

@InjectViewState
class SelectWeekBSPresenter : BasePresenter<MvpView>() {
    private val selectWeekController: SelectWeekController by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSelectWeekDialog")
    }

    fun select(week: Int) = selectWeekController.select(week)
}