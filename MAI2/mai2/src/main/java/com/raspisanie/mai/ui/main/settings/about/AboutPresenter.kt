package com.raspisanie.mai.ui.main.settings.about

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.devs.GetAllDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.LoadDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.SaveInRealmDevsUseCase
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.ui.ext.createHandler
import com.raspisanie.mai.ui.global.FlowGlobalFragment
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class AboutPresenter : BasePresenter<AboutView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val getAllDevsUseCase: GetAllDevsUseCase by inject()
    private val loadDevsUseCase: LoadDevsUseCase by inject()
    private val saveInRealmDevsUseCase: SaveInRealmDevsUseCase by inject()

    override fun attachView(view: AboutView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAbout")
        showDevs()
    }

    private fun showDevs() {
        val devs = getAllDevsUseCase()
        if (devs.size == 0) {
            viewState.toggleLoading(true)
        }
        viewState.showDevList(devs)
        getDevList()
    }

    private fun getDevList() {
        val handler = createHandler { viewState.toggleLoading(false) }
        launchUI(handler) {
            val realmList = withIO { loadDevsUseCase() }!!.toRealm()
            saveInRealmDevsUseCase(realmList)
            viewState.toggleLoading(false)
            viewState.showDevList(realmList.toLocal())
        }
    }

    fun back() = router?.exit()
}