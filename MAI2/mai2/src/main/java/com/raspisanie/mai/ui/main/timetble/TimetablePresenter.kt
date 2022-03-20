package com.raspisanie.mai.ui.main.timetble

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.TestDate
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.data.db.ext.*
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.ChangeBottomTabController
import com.raspisanie.mai.domain.controllers.SelectWeekController
import com.raspisanie.mai.common.extesions.getSemester
import com.raspisanie.mai.common.extesions.saveAuthState
import com.raspisanie.mai.common.extesions.saveSemester
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.domain.models.ScheduleLocal
import com.raspisanie.mai.domain.models.SelectWeekData
import com.raspisanie.mai.domain.models.SubjectLocal
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.mappers.toRealm
import com.raspisanie.mai.domain.usecases.groups.GetCurrentGroupUseCase
import com.raspisanie.mai.domain.usecases.groups.GroupIsSelectedUseCase
import com.raspisanie.mai.domain.usecases.main.RemoveRealmDataUseCase
import com.raspisanie.mai.domain.usecases.schedule.GetStorageScheduleUseCase
import com.raspisanie.mai.domain.usecases.schedule.LoadScheduleUseCase
import com.raspisanie.mai.domain.usecases.schedule.UpdateCurrentGroupScheduleUseCase
import com.raspisanie.mai.domain.usecases.state.GetSemesterUseCase
import com.raspisanie.mai.domain.usecases.state.SaveAuthStateUseCase
import com.raspisanie.mai.domain.usecases.state.SaveSemesterUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject
import timber.log.Timber
import java.util.*

@InjectViewState
class TimetablePresenter : BasePresenter<TimetableView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val bottomSheetDialogController: BottomSheetDialogController by inject()
    private val changeBottomTabController: ChangeBottomTabController by inject()
    private val getCurrentGroupUseCase: GetCurrentGroupUseCase by inject()
    private val groupIsSelectedUseCase: GroupIsSelectedUseCase by inject()
    private val getStorageScheduleUseCase: GetStorageScheduleUseCase by inject()
    private val selectWeekController: SelectWeekController by inject()
    private val loadScheduleUseCase: LoadScheduleUseCase by inject()
    private val updateCurrentGroupScheduleUseCase: UpdateCurrentGroupScheduleUseCase by inject()
    private val saveSemesterUseCase: SaveSemesterUseCase by inject()
    private val getSemesterUseCase: GetSemesterUseCase by inject()
    private val saveAuthStateUseCase: SaveAuthStateUseCase by inject()
    private val removeRealmDataUseCase: RemoveRealmDataUseCase by inject()
    private val context: Context by inject()

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
        viewState.showGroup(getCurrentGroupUseCase())
        YandexMetrica.reportEvent("OpenTimetable")
    }

    /**
     * Вызов диалога ваыбора недели.
     */
    fun selectWeekDialog() {
        bottomSheetDialogController.show(
            BottomSheetDialogType.SELECT_WEEK,
            SelectWeekData(
                currentWeek,
                currentSchedule?.getCurrentWeek() == null
            )
        )
    }

    /**
     * Загрузка расписания из реалма.
     * Если расписание загружено то проверяется дата и если она
     * не корректна то выполняется загрузка нового расписания
     * иначе оно загружается из локального хранилища.
     */
    private fun loadFromRealm() {
        if (groupIsSelectedUseCase()) {
            val schedule = getStorageScheduleUseCase()
            if (schedule == null || schedule.lastUpdate != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                loadSchedule()
            } else {
                showWeekByCurrent()
            }
        } else {
            viewState.showEmptyGroups()
        }
    }

    /**
     * Загрузка с сервера.
     */
    fun loadSchedule() {
        val handler = createHandler {
            if (it.message.toString() == "schedule not found") {
                removeAndOpenNewGroup()
            } else {
                context.showToast(
                    R.drawable.ic_report_gmailerrorred,
                    context.getString(R.string.timetable_error),
                    true
                )
                if (getStorageScheduleUseCase() == null) {
                    viewState.showErrorLoading()
                } else {
                    showWeekByCurrent()
                }
            }
        }
        getCurrentGroupUseCase()?.let { group ->
            launchUI(handler) {
                val id = group.id!!
                viewState.toggleLoading(true)
                val scheduleRealm = withIO { loadScheduleUseCase(id) }.toRealm(group.id!!)
                viewState.toggleLoading(false)
                updateCurrentGroupScheduleUseCase(scheduleRealm)
                currentSchedule = scheduleRealm.toLocal()

                /*
                Проверка на сброс группы.
                По каким правилам меняется название группы не
                понятно, по этому что делать и по этому просто
                просим человека выбрать группу еще раз.
                Разве это сложно сделать один раз в год?
                 */
                val lastSem = getSemesterUseCase()
                val sem = TestDate.getScheduleSemester(currentSchedule!!, lastSem)
                saveSemesterUseCase(sem)
                if (lastSem == TestDate.SECOND && sem == TestDate.FIRST) {
                    removeAndOpenNewGroup()
                } else {
                    //ничего интересного, просто проходим дальше
                    context.showToast(R.drawable.ic_done_all, context.getString(R.string.timetable_success))
                    viewState.shoWeek(currentSchedule?.getCurrentWeek(), currentWeek)
                }
            }
        }
    }

    /**
     * Удаляем все и показываем красивый экран
     * c уведомлением о начале нового учебного года.
     */
    private fun removeAndOpenNewGroup() {
        removeRealmDataUseCase()
        saveAuthStateUseCase(false)
        router?.replaceScreen(Screens.NewGroup)
    }

    /**
     * Загрузка расписания из реалма по выбранной неделе.
     */
    private fun showWeekByCurrent() {
        viewState.toggleLoading(false)
        currentSchedule = getStorageScheduleUseCase()?.toLocal()
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
            OPEN_LECTOR_SCHEDULE -> {
                val subject = data as SubjectLocal
                router?.navigateTo(Screens.LectorSchedule(subject.teacher, subject.date))
            }
            OPEN_ONLINE -> {
                viewState.openOnlineLink(data as String)
            }
        }
    }

    /**
     * скип недели на следующую.
     * Если выбрана спеиальная неделя то идет поиск текушей,
     * а затем прибавление номера.
     */
    private fun nextWeek() {
        YandexMetrica.reportEvent("ClickNextWeek")
        if (currentWeek < 0) {
            currentWeek = currentSchedule?.getCurrentWeek()?.number!!
        }
        currentWeek++
        showWeekByCurrent()
    }

    /**
     * Переход к настройкам.
     */
    fun goToSettings() {
        changeBottomTabController.changeMainScreen(Screens.FlowSettings)
    }

    private fun listenWeekNumber() {
        selectWeekController.state
            .listen {
                if (it != SelectWeekController.OTHER_WEEK) {
                    currentWeek = it
                    showWeekByCurrent()
                } else {
                    router?.navigateTo(Screens.SelectNumber(currentSchedule?.getCurrentWeek()?.number))
                }
            }.connect()
    }

    fun back() {
        router?.exit()
    }

    companion object {
        const val GO_TO_NEXT_WEEK = 0
        const val OPEN_LECTOR_SCHEDULE = 1
        const val OPEN_ONLINE = 2
    }
}