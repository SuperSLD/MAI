package com.raspisanie.mai.ui.main.info.search_lector

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.models.TeacherLocal
import com.raspisanie.mai.domain.usecases.information.lector.SearchLectorsUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class SearchLectorPresenter : BasePresenter<SearchLectorView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val searchLectorsUseCase: SearchLectorsUseCase by inject()
    private var lastSearch = ""

    override fun attachView(view: SearchLectorView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSearchLector")
    }

    fun select(teacher: TeacherLocal) {
        YandexMetrica.reportEvent("LectorSelected")
        router?.navigateTo(Screens.LectorSchedule(teacher))
    }

    fun search(name: String = lastSearch) {
        lastSearch = name.ifEmpty { "#" }
        val handler = createHandler {
            viewState.showErrorLoading()
        }
        launchUI(handler) {
            viewState.toggleLoading(true)
            val list = withIO { searchLectorsUseCase(lastSearch) }
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }

    fun back() = router?.exit()
}