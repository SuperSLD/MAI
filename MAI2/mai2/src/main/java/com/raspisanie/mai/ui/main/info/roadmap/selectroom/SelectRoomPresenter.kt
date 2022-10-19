package com.raspisanie.mai.ui.main.info.roadmap.selectroom

import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.SelectRoomController
import online.jutter.roadmapview.data.models.map.RMRoom
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

class SelectRoomPresenter : BasePresenter<MvpView>() {

    private val selectRoomController: SelectRoomController by inject()

    fun onRoomClick(room: RMRoom) {
        selectRoomController.select(room)
        back()
    }

    fun back() {
        router?.exit()
    }
}