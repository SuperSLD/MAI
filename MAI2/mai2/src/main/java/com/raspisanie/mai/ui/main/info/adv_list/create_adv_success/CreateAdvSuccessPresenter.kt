package com.raspisanie.mai.ui.main.info.adv_list.create_adv_success

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.yandex.metrica.YandexMetrica
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter

@InjectViewState
class CreateAdvSuccessPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("SuccessCreateAdv")
    }

    fun back() = router?.exit()
}