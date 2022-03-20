package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.SelectDateController
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.mappers.toRealm
import com.raspisanie.mai.domain.models.DayLocal
import com.raspisanie.mai.domain.models.ScheduleLocal
import com.raspisanie.mai.domain.models.TeacherLocal
import com.raspisanie.mai.domain.usecases.information.lector.LoadLectorScheduleUseCase
import com.raspisanie.mai.extesions.getUUID
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class LectorSchedulePresenter(
    private val lector: TeacherLocal,
    day: String
) : BasePresenter<LectorScheduleView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val selectDateController: SelectDateController by inject()
    private val loadLectorScheduleUseCase: LoadLectorScheduleUseCase by inject()

    private var schedule: ScheduleLocal? = null
    private var selectedDate = day

    override fun attachView(view: LectorScheduleView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenLectorSchedule")
        listenDate()
        load()
    }

    fun openCalendar() {
        router?.navigateTo(Screens.Calendar(
            start = schedule!!.weeks.first().days.first().date,
            end = schedule!!.weeks.last().days.last().date
        ))
    }

    fun load() {
        launchUI(CoroutineExceptionHandler { _, _ ->
            viewState.showErrorLoading()
        }) {
            viewState.toggleLoading(true)
            val schedule = withIO { loadLectorScheduleUseCase(lector.id) }
            viewState.toggleLoading(false)
            viewState.toggleLoading(false)
            this.schedule = schedule.toRealm(getUUID()).toLocal()
            if (selectedDate.isEmpty()) {
                viewState.showSchedule(
                    this.schedule?.getCurrentWeek()?.getCurrentDay() ?: DayLocal(
                        "",
                        "",
                        mutableListOf()
                    )
                )
            } else {
                viewState.showSchedule(this.schedule?.findDay(selectedDate) ?: DayLocal("", selectedDate, mutableListOf()))
            }
        }
    }

    fun next() {
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()

    private fun listenDate() {
        selectDateController.state
            .listen {
                selectedDate = it
                viewState.showSchedule(schedule?.findDay(it) ?: DayLocal("", it, mutableListOf()))
            }.connect()
    }
}