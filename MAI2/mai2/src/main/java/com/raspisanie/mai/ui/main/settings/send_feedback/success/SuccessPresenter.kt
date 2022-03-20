package com.raspisanie.mai.ui.main.settings.send_feedback.success

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class SuccessPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("SuccessSendFeedback")
    }

    fun back() = router?.exit()
}