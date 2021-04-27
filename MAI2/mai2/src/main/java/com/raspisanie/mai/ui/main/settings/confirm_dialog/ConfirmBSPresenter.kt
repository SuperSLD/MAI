package com.raspisanie.mai.ui.main.settings.confirm_dialog

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.ConfirmController
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class ConfirmBSPresenter : BasePresenter<MvpView>() {
    private val confirmController: ConfirmController by inject()

    fun confirm() = confirmController.confirm(true)
    fun cancel() = confirmController.confirm(false)
}