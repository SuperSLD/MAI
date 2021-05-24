package com.raspisanie.mai.ui.main.exams

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import com.yandex.metrica.YandexMetrica
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter

@InjectViewState
class ExamsPresenter : BasePresenter<ExamsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val realm: Realm by inject()

    override fun attachView(view: ExamsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        viewState.showExams(realm.getCurrentSchedule()?.toLocal()?.extractExams())
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
    fun onDaysListItemClick(action: Int, data: Any?) {}

    fun back() {
        router?.exit()
    }
}