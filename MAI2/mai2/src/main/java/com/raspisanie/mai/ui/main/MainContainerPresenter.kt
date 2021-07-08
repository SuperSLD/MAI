package com.raspisanie.mai.ui.main

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.common.TestDate
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.ChangeBottomTabController
import com.raspisanie.mai.controllers.NotificationController
import com.raspisanie.mai.extesions.*
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.clearScheduleForCurrentGroup
import com.raspisanie.mai.extesions.realm.getCurrentGroup
import com.raspisanie.mai.extesions.realm.getCurrentSchedule
import com.raspisanie.mai.extesions.realm.updateSchedule
import com.raspisanie.mai.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class MainContainerPresenter : BasePresenter<MainContainerView>() {

    private val changeBottomTabController: ChangeBottomTabController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val notificationController: NotificationController by inject()
    private val service: ApiService by inject()
    private val context: Context by inject()

    override fun attachView(view: MainContainerView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        viewState.initBottomNavigation()
        viewState.showNotifications(context.getNotifications())
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.changeBottomTab(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    private fun listenBottomNavigationVisibility() {
        bottomVisibilityController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.changeBottomNavigationVisibility(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    /**
     * Получаем количесво уведомлений.
     */
    private fun loadNotifications() {
        val oldData = context.getNotifications()
        service.getNotifications(oldData.lastUpdate)
            .map { if (it.success) it.data else error(it.message.toString()) }
            .map { it!! }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    /*
                    После этих изменений старые данные
                    становятся новыми, но за ними тянется их
                    прежнее название (
                     */
                    oldData.setNewsCount(it.newsCount + oldData.getNewsCount())
                    oldData.updateDate()
                    context.saveNotifications(oldData)
                    notificationController.show(oldData)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    private fun listenNotifications() {
        notificationController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.showNotifications(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

}