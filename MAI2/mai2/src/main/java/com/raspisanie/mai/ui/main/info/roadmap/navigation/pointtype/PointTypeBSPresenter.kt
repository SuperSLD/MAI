package com.raspisanie.mai.ui.main.info.roadmap.navigation.pointtype

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.ConfirmController
import com.raspisanie.mai.domain.controllers.PointType
import com.raspisanie.mai.domain.controllers.PointTypeController
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class PointTypeBSPresenter : BasePresenter<MvpView>() {
    private val pointTypeController: PointTypeController by inject()

    fun mapPoint() = pointTypeController.select(PointType.MAP)
    fun roomPoint() = pointTypeController.select(PointType.ROOM)
}