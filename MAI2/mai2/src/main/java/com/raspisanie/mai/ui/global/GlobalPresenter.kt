package com.raspisanie.mai.ui.global

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.domain.controllers.ShowToastController
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class GlobalPresenter : BasePresenter<GlobalView>() {
    private val bottomSheetDialogController: BottomSheetDialogController by inject()
    private val showToastController: ShowToastController by  inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenBottomSheet()
        listenToast()
    }

    private fun listenToast() {
        showToastController.state
            .listen {
                viewState.showToast(it.first, it.second)
            }.connect()
    }

    private fun listenBottomSheet() {
        bottomSheetDialogController.state
            .listen {
                viewState.showBottomSheet(it.first, it.second)
            }.connect()
    }
}