package com.raspisanie.mai.ui.main.timetble.select_number

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.SelectWeekController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.yandex.metrica.YandexMetrica
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter

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