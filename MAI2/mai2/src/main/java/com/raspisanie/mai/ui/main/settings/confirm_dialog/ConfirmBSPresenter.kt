package com.raspisanie.mai.ui.main.settings.confirm_dialog

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.ConfirmController
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class ConfirmBSPresenter : BasePresenter<MvpView>() {
    private val confirmController: ConfirmController by inject()

    fun confirm() = confirmController.confirm(true)
    fun cancel() = confirmController.confirm(false)
}