package com.raspisanie.mai.ui.view.form.selector_bs

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import pro.midev.supersld.common.base.BasePresenter

@InjectViewState
class SelectorBSPresenter : BasePresenter<MvpView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //YandexMetrica.reportEvent("OpenSelectWeekDialog")
    }

    //fun select(week: Int) = selectWeekController.select(week)
}