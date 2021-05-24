package com.raspisanie.mai.ui.main.timetble.select_number

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.SelectWeekController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.raspisanie.mai.extesions.realm.updateGroup
import com.raspisanie.mai.extesions.saveAuthState
import com.raspisanie.mai.models.realm.GroupRealm
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
class SelectNumberPresenter(

) : BasePresenter<SelectNumberView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val realm: Realm by inject()
    private val selectWeekController: SelectWeekController by inject()

    override fun attachView(view: SelectNumberView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showList(realm.getCurrentSchedule()?.toLocal()?.weeks)
        YandexMetrica.reportEvent("OpenSelectWeekNumber")
    }

    fun select(number: Int) {
        YandexMetrica.reportEvent("ClickWeekNumber")
        selectWeekController.select(number)
        back()
    }

    fun back() = router?.exit()
}