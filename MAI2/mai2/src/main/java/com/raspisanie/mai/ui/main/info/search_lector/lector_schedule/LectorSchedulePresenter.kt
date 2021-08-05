package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

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
class LectorSchedulePresenter(
    private val lector: TeacherLocal,
    day: String
) : BasePresenter<LectorScheduleView>() {

    private val service: ApiService by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val selectDateController: SelectDateController by inject()

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
        service.getLectorSchedule(lector.id)
                .map { if (it.success) it.data else error(it.message.toString()) }
                .map { it!!.toRealm(getUUID()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnError {
                    it.printStackTrace()
                    viewState.showErrorLoading()
                }
                .subscribe(
                        {
                            viewState.toggleLoading(false)
                            this.schedule = it.toLocal()
                            if (selectedDate.isEmpty()) {
                                viewState.showSchedule(
                                    schedule?.getCurrentWeek()?.getCurrentDay() ?: DayLocal(
                                        "",
                                        "",
                                        mutableListOf()
                                    )
                                )
                            } else {
                                viewState.showSchedule(schedule?.findDay(selectedDate) ?: DayLocal("", selectedDate, mutableListOf()))
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun next() {
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()

    private fun listenDate() {
        selectDateController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    selectedDate = it
                    viewState.showSchedule(schedule?.findDay(it) ?: DayLocal("", it, mutableListOf()))
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }
}