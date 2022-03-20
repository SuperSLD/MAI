package com.raspisanie.mai.ui.splash

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.extesions.getAuthState
import com.yandex.metrica.YandexMetrica
import io.realm.Realm
import kotlinx.coroutines.delay
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchIO
import online.jutter.supersld.extensions.withUI
import org.koin.core.inject

class SplashPresenter : BasePresenter<MvpView>() {

    private val context : Context by inject()
    private val realm: Realm by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenApp")
        start()
    }

    private fun start() {
        launchIO {
            delay(2000)
            withUI {
                if (context.getAuthState()) {
                    router?.newRootScreen(Screens.FlowMain)
                } else {
                    realm.executeTransaction {
                        realm.deleteAll()
                    }
                    router?.newRootScreen(Screens.FlowSelectGroup)
                }
            }
        }
    }

    fun back() {
        router?.exit()
    }
}