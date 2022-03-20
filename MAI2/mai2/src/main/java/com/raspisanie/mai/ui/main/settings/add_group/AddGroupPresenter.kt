package com.raspisanie.mai.ui.main.settings.add_group

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.mappers.toRealm
import com.raspisanie.mai.domain.usecases.groups.GetCurrentGroupUseCase
import com.raspisanie.mai.domain.usecases.groups.SearchGroupsUseCase
import com.raspisanie.mai.domain.usecases.groups.UpdateGroupUseCase
import com.raspisanie.mai.ui.select_group.select_group.SelectGroupView
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class AddGroupPresenter : BasePresenter<SelectGroupView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private var lastSearch = ""
    private val updateGroupUseCase: UpdateGroupUseCase by inject()
    private val getCurrentGroupUseCase: GetCurrentGroupUseCase by inject()
    private val searchGroupsUseCase: SearchGroupsUseCase by inject()

    override fun attachView(view: SelectGroupView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAddGroup")
    }

    fun select(group: GroupRealm) {
        if (getCurrentGroupUseCase()?.id != group.id) {
            group.selected = false
            YandexMetrica.reportEvent("AddGroupSuccess")
            updateGroupUseCase(group)
        }
        back()
    }

    fun search(name: String = lastSearch) {
        lastSearch = name.ifEmpty { "#" }
        launchUI(CoroutineExceptionHandler { _, _ ->
            viewState.showErrorLoading()
        }) {
            viewState.toggleLoading(true)
            val list = withIO{ searchGroupsUseCase(lastSearch) } ?: mutableListOf()
            viewState.toggleLoading(false)
            viewState.showList(list.map { it.toRealm() }.toMutableList())
        }
    }

    fun next() {
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()
}