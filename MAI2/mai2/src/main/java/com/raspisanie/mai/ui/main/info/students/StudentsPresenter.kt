package com.raspisanie.mai.ui.main.info.students

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.LoadStudOrgUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class StudentsPresenter : BasePresenter<StudentsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val loadStudOrgUseCase: LoadStudOrgUseCase by inject()

    override fun attachView(view: StudentsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenStudents")
        loadList()
    }

    fun back() = router?.exit()

    fun loadList() {
        val handler = createHandler { viewState.showErrorLoading() }
        launchUI(handler) {
            viewState.toggleLoading(true)
            val list = withIO { loadStudOrgUseCase() }
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }
}