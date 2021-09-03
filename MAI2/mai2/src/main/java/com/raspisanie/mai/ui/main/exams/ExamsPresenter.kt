package com.raspisanie.mai.ui.main.exams

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.ChangeBottomTabController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.realm.getAllGroup
import com.raspisanie.mai.extesions.realm.getCurrentGroup
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.raspisanie.mai.models.local.SubjectLocal
import com.raspisanie.mai.ui.main.timetble.TimetablePresenter
import com.yandex.metrica.YandexMetrica
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import javax.security.auth.Subject

@InjectViewState
class ExamsPresenter : BasePresenter<ExamsView>() {

    private val changeBottomTabController: ChangeBottomTabController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val realm: Realm by inject()

    override fun attachView(view: ExamsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        if (realm.getAllGroup().isNotEmpty() && realm.getCurrentGroup() != null) {
            viewState.showExams(realm.getCurrentSchedule()?.toLocal()?.extractExams())
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