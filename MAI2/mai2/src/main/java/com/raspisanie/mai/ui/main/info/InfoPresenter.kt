package com.raspisanie.mai.ui.main.info

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.NotificationController
import com.raspisanie.mai.extesions.getNotifications
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class InfoPresenter : BasePresenter<InfoView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val notificationController: NotificationController by inject()
    private val context: Context by inject()

    override fun attachView(view: InfoView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        viewState.showNotifications(context.getNotifications())
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenInfo")
        listenNotifications()
    }

    fun openNews() = router?.navigateTo(Screens.News)
    fun openCampusMap() = router?.navigateTo(Screens.CampusMap)
    fun openCanteens() = router?.navigateTo(Screens.Canteens)
    fun openLibrary() = router?.navigateTo(Screens.Library)
    fun openSport() = router?.navigateTo(Screens.Sport)
    fun openStudents() = router?.navigateTo(Screens.Students)
    fun openCreative() = router?.navigateTo(Screens.Creative)

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

    fun back() {
        router?.exit()
    }
}