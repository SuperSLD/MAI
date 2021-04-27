package com.raspisanie.mai.ui.main.timetble

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.controllers.BottomVisibilityController
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
    private var currentSchedule: ScheduleLocal? = null
    private val service: ApiService by inject()

    override fun attachView(view: TimetableView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        loadFromRealm()
    }

    fun selectWeekDialog() {
        bottomSheetDialogController.show(BottomSheetDialogType.SELECT_WEEK)
    }

    private fun loadFromRealm() {
        val schedule = realm.getCurrentSchedule()
        if (schedule == null || schedule.lastUpdate != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            loadSchedule()
        } else {
            currentSchedule = schedule.toLocal()
            viewState.shoWeek(currentSchedule?.getCurrentWeek())
            Timber.d("load week from schedule: ${currentSchedule?.groupId}")
        }
    }

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
                        viewState.showErrorLoading()
                    }
                    .subscribe(
                            {
                                val scheduleRealm = it.toRealm(group.id!!)
                                viewState.toggleLoading(false)
                                realm.clearScheduleForCurrentGroup()
                                realm.updateSchedule(scheduleRealm)
                                currentSchedule = scheduleRealm.toLocal()
                                viewState.shoWeek(currentSchedule?.getCurrentWeek())
                            },
                            {
                                Timber.e(it)
                                currentSchedule = realm.getCurrentSchedule()?.toLocal()
                                viewState.shoWeek(currentSchedule?.getCurrentWeek())
                            }
                    ).connect()
        }
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

            }
        }
    }

    fun back() {
        router?.exit()
    }

    companion object {
        const val GO_TO_NEXT_WEEK = 0
        const val EVENT_DETAIL_OPEN = 1
        const val EVENT_HIDE = 2
        const val DAY_SUBJECT = 3
    }
}