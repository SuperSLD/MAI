package com.raspisanie.mai.ui.main.info.roadmap.selectpoint

import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.SelectMarkerController
import online.jutter.roadmapview.data.models.map.RMMarker
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

class SelectPointPresenter : BasePresenter<MvpView>() {

    private val selectMarkerController: SelectMarkerController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun onDoneClick(marker: RMMarker) {
        selectMarkerController.select(marker)
        back()
    }

    fun back() {
        router?.exit()
    }
}