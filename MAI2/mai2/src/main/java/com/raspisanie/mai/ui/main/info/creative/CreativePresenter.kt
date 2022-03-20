package com.raspisanie.mai.ui.main.info.creative

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.LoadCreativeGroupsUseCase
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class CreativePresenter : BasePresenter<CreativeView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val loadCreativeGroupsUseCase: LoadCreativeGroupsUseCase by inject()

    override fun attachView(view: CreativeView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenCreative")
        loadList()
    }

    fun loadList() {
        launchUI(CoroutineExceptionHandler { _, _ ->
            viewState.showErrorLoading()
        }) {
            viewState.toggleLoading(true)
            val list = withIO { loadCreativeGroupsUseCase() }
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }
    fun back() = router?.exit()
}