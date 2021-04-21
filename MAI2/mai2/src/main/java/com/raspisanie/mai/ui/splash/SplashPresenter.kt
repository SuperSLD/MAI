package com.raspisanie.mai.ui.splash

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.extesions.getAuthState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashPresenter : BasePresenter<MvpView>() {

    private val context : Context by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        start()
    }

    private fun start() {
        Single
            .timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (context.getAuthState()) {
                       router?.newRootScreen(Screens.FlowMain)
                    } else {
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