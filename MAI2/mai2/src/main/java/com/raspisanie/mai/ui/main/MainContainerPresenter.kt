package com.raspisanie.mai.ui.main

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.ChangeBottomTabController
import com.raspisanie.mai.domain.controllers.NotificationController
import com.raspisanie.mai.domain.usecases.main.LoadNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchIO
import online.jutter.supersld.extensions.withUI
import org.koin.core.inject

@InjectViewState
class MainContainerPresenter : BasePresenter<MainContainerView>() {

    private val changeBottomTabController: ChangeBottomTabController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val notificationController: NotificationController by inject()
    private val getNotificationUseCase: GetNotificationsUseCase by inject()
    private val loadNotificationsUseCase: LoadNotificationsUseCase by inject()

    override fun attachView(view: MainContainerView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        viewState.initBottomNavigation()
        viewState.showNotifications(getNotificationUseCase())
        loadNotifications()

    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenChangeBottomTab()
        listenBottomNavigationVisibility()
        listenNotifications()
    }


    private fun listenChangeBottomTab() {
        changeBottomTabController.state
            .listen {
                viewState.changeBottomTab(it)
            }.connect()
    }

    private fun listenBottomNavigationVisibility() {
        bottomVisibilityController.state
            .listen {
                viewState.changeBottomNavigationVisibility(it)
            }.connect()
    }

    /**
     * Получаем количесво уведомлений.
     */
    private fun loadNotifications() {
        launchIO {
            val updatedNotifications = loadNotificationsUseCase()
            withUI {
                notificationController.show(updatedNotifications)
            }
        }
    }

    private fun listenNotifications() {
        notificationController.state
            .listen {
                viewState.showNotifications(it)
            }.connect()
    }

}