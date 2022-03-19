package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.calendar

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.SelectDateController
import com.yandex.metrica.YandexMetrica
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter

@InjectViewState
class CalendarPresenter : BasePresenter<CalendarView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val selectDateController: SelectDateController by inject()

    override fun attachView(view: CalendarView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun selectDate(date: String) {
        selectDateController.select(date)
        back()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenCalendar")
    }

    fun back() = router?.exit()
}