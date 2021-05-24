package com.raspisanie.mai.ui.splash

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.extesions.getAuthState
import com.raspisanie.mai.extesions.realm.getCurrentGroup
import com.raspisanie.mai.extesions.saveAuthState
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashPresenter : BasePresenter<MvpView>() {

    private val context : Context by inject()
    private val realm: Realm by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenApp")
        start()
    }

    private fun start() {
        Single
            .timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    //if (false) {
                    if (context.getAuthState() && realm.getCurrentGroup() != null) {
                       router?.newRootScreen(Screens.FlowMain)
                    } else {
                        realm.executeTransaction {
                            realm.deleteAll()
                        }
                        router?.newRootScreen(Screens.FlowSelectGroup)
                    }
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