package com.raspisanie.mai.ui.main.exams

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.ChangeBottomTabController
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.models.SubjectLocal
import com.raspisanie.mai.domain.usecases.groups.GetAllGroupsUseCase
import com.raspisanie.mai.domain.usecases.groups.GetCurrentGroupUseCase
import com.raspisanie.mai.domain.usecases.schedule.GetStorageScheduleUseCase
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class ExamsPresenter : BasePresenter<ExamsView>() {

    private val changeBottomTabController: ChangeBottomTabController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val getAllGroupsUseCase: GetAllGroupsUseCase by inject()
    private val getCurrentGroupUseCase: GetCurrentGroupUseCase by inject()
    private val getStorageScheduleUseCase: GetStorageScheduleUseCase by inject()

    override fun attachView(view: ExamsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        if (getAllGroupsUseCase().isNotEmpty() && getCurrentGroupUseCase() != null) {
            viewState.showExams(getStorageScheduleUseCase()?.toLocal()?.extractExams())
        } else {
            viewState.showEmptyGroups()
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenExams")
    }

    /**
     * Обработка действий со списком в расписании.
     * @param action действие
     * @param data данные передаваемые вместе с действием
     */
    fun onDaysListItemClick(action: Int, data: Any?) {
        when(action) {
            TimetablePresenter.OPEN_LECTOR_SCHEDULE -> {
                val subject = data as SubjectLocal
                router?.navigateTo(Screens.LectorSchedule(subject.teacher, subject.date))
            }
        }
    }

    /**
     * Переход к настройкам.
     */
    fun goToSettings() {
        changeBottomTabController.changeMainScreen(Screens.FlowSettings)
    }

    fun back() {
        router?.exit()
    }
}