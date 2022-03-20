package com.raspisanie.mai.ui.select_group.select_group

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.data.db.ext.updateGroup
import com.raspisanie.mai.common.extesions.saveAuthState
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.domain.mappers.toRealm
import com.raspisanie.mai.domain.usecases.groups.SearchGroupsUseCase
import com.raspisanie.mai.domain.usecases.groups.UpdateGroupUseCase
import com.raspisanie.mai.domain.usecases.state.SaveAuthStateUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import io.realm.Realm
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class SelectGroupPresenter : BasePresenter<SelectGroupView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val searchGroupsUseCase: SearchGroupsUseCase by inject()
    private val saveAuthStateUseCase: SaveAuthStateUseCase by inject()
    private val updateGroupUseCase: UpdateGroupUseCase by inject()
    private var lastSearch = ""

    override fun attachView(view: SelectGroupView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSelectGroup")
    }

    fun select(group: GroupRealm) {
        YandexMetrica.reportEvent("FirstGroupSelected")
        saveAuthStateUseCase(true)
        group.selected = true
        updateGroupUseCase(group)
        router?.newRootScreen(Screens.FlowMain)
    }

    fun search(name: String = lastSearch) {
        lastSearch = name.ifEmpty { "#" }
        val handler = createHandler { viewState.showErrorLoading() }
        launchUI(handler) {
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