package com.raspisanie.mai.ui.select_group.select_group

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class SelectGroupPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun next() {
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()
}