package com.raspisanie.mai.ui.main.info

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import com.raspisanie.mai.controllers.BottomVisibilityController
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class InfoPresenter : BasePresenter<MvpView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    fun openCampusMap() = router?.navigateTo(Screens.CampusMap)
    fun openCanteens() = router?.navigateTo(Screens.Canteens)

    fun back() {
        router?.exit()
    }
}