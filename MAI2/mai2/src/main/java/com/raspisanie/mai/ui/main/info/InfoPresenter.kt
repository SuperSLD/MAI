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
    fun openLibrary() = router?.navigateTo(Screens.Library)
    fun openSport() = router?.navigateTo(Screens.Sport)
    fun openStudents() = router?.navigateTo(Screens.Students)
    fun openCreative() = router?.navigateTo(Screens.Creative)

    fun back() {
        router?.exit()
    }
}