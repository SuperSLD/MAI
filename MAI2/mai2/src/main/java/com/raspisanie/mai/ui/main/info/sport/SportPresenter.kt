package com.raspisanie.mai.ui.main.info.sport

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.LoadSportSectionsUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class SportPresenter : BasePresenter<SportView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val loadSportSectionsUseCase: LoadSportSectionsUseCase by inject()

    override fun attachView(view: SportView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSport")
        loadList()
    }

    fun loadList() {
        val handler = createHandler {
            viewState.showErrorLoading()
        }
        launchUI(handler) {
            viewState.toggleLoading(true)
            val list = withIO { loadSportSectionsUseCase() }
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }

    fun back() = router?.exit()
}