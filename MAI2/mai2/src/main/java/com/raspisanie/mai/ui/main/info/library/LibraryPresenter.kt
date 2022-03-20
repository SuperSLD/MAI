package com.raspisanie.mai.ui.main.info.library

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.LoadLibraryUseCase
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class LibraryPresenter : BasePresenter<LibraryView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val loadLibraryUseCase: LoadLibraryUseCase by inject()

    override fun attachView(view: LibraryView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenLibrary")
        loadList()
    }

    fun loadList() {
        launchUI(CoroutineExceptionHandler { _, _ ->
            viewState.showErrorLoading()
        }) {
            viewState.toggleLoading(true)
            val list = withIO { loadLibraryUseCase() }
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }


    fun back() = router?.exit()
}