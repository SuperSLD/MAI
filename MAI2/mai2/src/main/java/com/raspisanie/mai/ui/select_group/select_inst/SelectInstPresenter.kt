package com.raspisanie.mai.ui.select_group.select_inst

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BasePresenter
import com.raspisanie.mai.controllers.BottomVisibilityController
import org.koin.core.inject
import com.raspisanie.mai.common.CiceroneHolder
import ru.terrakok.cicerone.Router

@InjectViewState
class SelectInstPresenter : BasePresenter<MvpView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun next() {
        router?.navigateTo(Screens.SelectGroup)
    }

    fun back() = router?.exit()
}