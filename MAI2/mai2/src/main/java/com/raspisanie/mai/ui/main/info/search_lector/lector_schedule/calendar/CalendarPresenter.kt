package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.calendar

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.SelectDateController
import com.raspisanie.mai.controllers.SelectWeekController
import com.raspisanie.mai.extesions.getUUID
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.updateGroup
import com.raspisanie.mai.extesions.saveAuthState
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.ScheduleLocal
import com.raspisanie.mai.models.local.TeacherLocal
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.realm.ScheduleRealm
import com.raspisanie.mai.models.realm.WeekRealm
import com.raspisanie.mai.server.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router
import timber.log.Timber

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