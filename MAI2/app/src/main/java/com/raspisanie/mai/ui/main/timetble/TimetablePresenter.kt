package com.raspisanie.mai.ui.main.timetble

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import com.raspisanie.mai.common.CiceroneHolder
import com.raspisanie.mai.common.base.BasePresenter
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.getAuthState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class TimetablePresenter : BasePresenter<TimetableView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val context : Context by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val bottomSheetDialogController: BottomSheetDialogController by inject()

    override fun attachView(view: TimetableView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    fun selectWeekDialog() {
        bottomSheetDialogController.show(BottomSheetDialogType.SELECT_WEEK)
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