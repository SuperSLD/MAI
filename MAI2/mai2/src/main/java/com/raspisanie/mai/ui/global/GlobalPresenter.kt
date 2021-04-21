package com.raspisanie.mai.ui.global

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.common.base.BottomSheetDialogController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class GlobalPresenter : BasePresenter<GlobalView>() {
    private val bottomSheetDialogController: BottomSheetDialogController by inject()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenBottomSheet()
    }

    private fun listenBottomSheet() {
        bottomSheetDialogController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.showBottomSheet(it.first, it.second)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }
}