package com.raspisanie.mai.ui.main.info.roadmap

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.NavigationController
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class RoadMapPresenter : BasePresenter<RoadMapView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationController: NavigationController by inject()

    override fun attachView(view: RoadMapView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
        navigationController.get()?.let { viewState.findRoad(it) }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenRoadMap")
    }

    fun clearNavData() {
        navigationController.clearData()
    }

    fun onOpenNavPoints() {
        router?.navigateTo(Screens.NavPoints)
    }

    fun back() {
        navigationController.clearData()
        router?.exit()
    }
}