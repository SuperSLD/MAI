package com.raspisanie.mai.ui.main.timetble.new_group

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.ui.global.FlowGlobalFragment
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class NewGroupPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val mainCicerone = navigationHolder.getCicerone(FlowGlobalFragment.ROUTER)

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("NewGroupOpen")
    }

    fun openStart() {
        mainCicerone?.router?.newRootScreen(Screens.FlowSelectGroup)
    }

    fun back() = router?.exit()
}