package com.raspisanie.mai.ui.splash

import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.usecases.main.RemoveRealmDataUseCase
import com.raspisanie.mai.domain.usecases.state.GetAuthStateUseCase
import com.raspisanie.mai.ui.ext.createEmptyHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.delay
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchIO
import online.jutter.supersld.extensions.withUI
import org.koin.core.inject

class SplashPresenter : BasePresenter<MvpView>() {

    private val removeRealmDataUseCase: RemoveRealmDataUseCase by inject()
    private val getAuthStateUseCase: GetAuthStateUseCase by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenApp")
        start()
    }

    private fun start() {
        launchIO(createEmptyHandler()) {
            delay(2000)
            withUI {
                if (getAuthStateUseCase()) {
                    router?.newRootScreen(Screens.FlowMain)
                } else {
                    removeRealmDataUseCase()
                    router?.newRootScreen(Screens.FlowSelectGroup)
                }
            }
        }
    }

    fun back() {
        router?.exit()
    }
}