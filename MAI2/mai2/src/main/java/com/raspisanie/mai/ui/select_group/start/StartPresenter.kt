package com.raspisanie.mai.ui.select_group.start

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.common.extesions.saveAuthState
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class StartPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val context: Context by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenStart")
    }

    fun goSelectYear() {
        router?.navigateTo(Screens.SelectGroup)
    }

    fun skip() {
        context.saveAuthState(true)
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()
}