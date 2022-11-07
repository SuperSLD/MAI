package com.raspisanie.mai.ui.main.info.roadmap.selectroom

import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.SelectRoomController
import com.raspisanie.mai.ui.main.info.roadmap.RoadMapView
import online.jutter.roadmapview.data.models.map.RMRoom
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

class SelectRoomPresenter : BasePresenter<MvpView>() {

    private val selectRoomController: SelectRoomController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun onRoomClick(room: RMRoom) {
        selectRoomController.select(room)
        back()
    }

    fun back() {
        router?.exit()
    }
}