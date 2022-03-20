package com.raspisanie.mai.ui.main.info

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.NotificationController
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class InfoPresenter : BasePresenter<InfoView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val notificationController: NotificationController by inject()
    private val getNotificationUseCase: GetNotificationsUseCase by inject()

    override fun attachView(view: InfoView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        viewState.showNotifications(getNotificationUseCase())
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenInfo")
        listenNotifications()
    }

    fun openAdvList() = router?.navigateTo(Screens.AdvList)
    fun openNews() = router?.navigateTo(Screens.News)
    fun openLectors() = router?.navigateTo(Screens.SearchLector)
    fun openCampusMap() = router?.navigateTo(Screens.CampusMap)
    fun openCanteens() = router?.navigateTo(Screens.Canteens)
    fun openLibrary() = router?.navigateTo(Screens.Library)
    fun openSport() = router?.navigateTo(Screens.Sport)
    fun openStudents() = router?.navigateTo(Screens.Students)
    fun openCreative() = router?.navigateTo(Screens.Creative)

    private fun listenNotifications() {
        notificationController.state
            .listen {
                viewState.showNotifications(it)
            }.connect()
    }

    fun back() {
        router?.exit()
    }
}