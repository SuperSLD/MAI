package com.raspisanie.mai.ui.main.timetble

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.SelectWeekController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.clearScheduleForCurrentGroup
import com.raspisanie.mai.extesions.realm.getCurrentGroup
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.raspisanie.mai.extesions.realm.updateSchedule
import com.raspisanie.mai.models.local.ScheduleLocal
import com.raspisanie.mai.models.realm.ScheduleRealm
import com.raspisanie.mai.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*

@InjectViewState
class TimetablePresenter : BasePresenter<TimetableView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val bottomSheetDialogController: BottomSheetDialogController by inject()
    private val realm: Realm by inject()
    private val service: ApiService by inject()
    private val selectWeekController: SelectWeekController by inject()

    private var currentSchedule: ScheduleLocal? = null
    private var currentWeek = SelectWeekController.THIS_WEEK

    override fun attachView(view: TimetableView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenWeekNumber()
        loadFromRealm()
    }

    /**
     * Вызов диалога ваыбора недели.
     */
    fun selectWeekDialog() {
        bottomSheetDialogController.show(BottomSheetDialogType.SELECT_WEEK, currentWeek)
    }

    /**
     * Загрузка расписания из реалма.
     * Если расписание загружено то проверяется дата и если она
     * не корректна то выполняется загрузка нового расписания
     * иначе оно загружается из локального хранилища.
     */
    private fun loadFromRealm() {
        val schedule = realm.getCurrentSchedule()
        if (schedule == null || schedule.lastUpdate != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            loadSchedule()
        } else {
            showWeekByCurrent()
            Timber.d("load week from schedule: ${currentSchedule?.groupId}")
        }
    }

    /**
     * Загрузка с сервера.
     */
    fun loadSchedule() {
        realm.getCurrentGroup()?.let { group ->
            service.getSchedule(group.id!!)
                    .map { if (it.success) it.data else error(it.message.toString()) }
                    .map { it!! }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { viewState.toggleLoading(true) }
                    .doOnError {
                        it.printStackTrace()
                        if (realm.getCurrentSchedule() == null) {
                            viewState.showErrorLoading()
                        } else {
                            showWeekByCurrent()
                        }
                    }
                    .subscribe(
                            {
                                val scheduleRealm = it.toRealm(group.id!!)
                                viewState.toggleLoading(false)
                                realm.clearScheduleForCurrentGroup()
                                realm.updateSchedule(scheduleRealm)
                                currentSchedule = scheduleRealm.toLocal()
                                viewState.shoWeek(currentSchedule?.getCurrentWeek(), currentWeek)
                            },
                            {
                                Timber.e(it)
                                showWeekByCurrent()
                            }
                    ).connect()
        }
    }

    /**
     * Загрузка расписания из реалма по выбранной неделе.
     */
    private fun showWeekByCurrent() {
        currentSchedule = realm.getCurrentSchedule()?.toLocal()
        viewState.shoWeek(
                when(currentWeek) {
                    SelectWeekController.THIS_WEEK -> currentSchedule?.getCurrentWeek()
                    SelectWeekController.PREVIOUS_WEEK -> currentSchedule?.getPreviousWeek()
                    SelectWeekController.NEXT_WEEK -> currentSchedule?.getNextWeek()
                    else -> currentSchedule?.getWeek(currentWeek)
                },
                currentWeek
        )
        viewState.showTitle(currentWeek)
    }

    fun onDayHeaderClick(date: String) {
        viewState.selectDay(date)
    }

    /**
     * Обработка действий со списком в расписании.
     * @param action действие
     * @param data данные передаваемые вместе с действием
     */
    fun onDaysListItemClick(action: Int, data: Any?) {
        when(action) {
            GO_TO_NEXT_WEEK -> {
                nextWeek()
            }
        }
    }

    /**
     * скип недели на следующую.
     * Если выбрана спеиальная неделя то идет поиск текушей,
     * а затем прибавление номера.
     */
    private fun nextWeek() {
        if (currentWeek < 0) {
            currentWeek = currentSchedule?.getCurrentWeek()?.number!!
        }
        currentWeek++
        showWeekByCurrent()
    }

    private fun listenWeekNumber() {
        selectWeekController.state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if (it != SelectWeekController.OTHER_WEEK) {
                                currentWeek = it
                                showWeekByCurrent()
                            } else {
                                router?.navigateTo(Screens.SelectNumber(currentSchedule?.getCurrentWeek()?.number!!))
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun back() {
        router?.exit()
    }

    companion object {
        const val GO_TO_NEXT_WEEK = 0
    }
}