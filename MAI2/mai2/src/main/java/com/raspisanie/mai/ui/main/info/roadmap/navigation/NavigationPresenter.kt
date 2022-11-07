package com.raspisanie.mai.ui.main.info.roadmap.navigation

import android.graphics.Color
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.domain.controllers.*
import online.jutter.roadmapview.data.models.map.RMMarker
import online.jutter.supersld.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class NavigationPresenter : BasePresenter<NavigationView>() {

    private val selectMarkerController: SelectMarkerController by inject()
    private val navigationController: NavigationController by inject()
    private val selectRoomController: SelectRoomController by inject()
    private val pointTypeController: PointTypeController by inject()
    private val bottomSheetDialogController: BottomSheetDialogController by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private var isStartMarker = false
    private var selectRoom = false

    override fun attachView(view: NavigationView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenMarker()
        listenPointType()
        listenRoom()
        val navData = navigationController.get()
        if (navData != null) {
            viewState.addStartMarker(navData.first)
            viewState.addEndMarker(navData.second)
        }
    }

    fun onSelectStart() {
        isStartMarker = true
        bottomSheetDialogController.show(BottomSheetDialogType.POINT_TYPE)
    }

    fun onSelectEnd() {
        isStartMarker = false
        bottomSheetDialogController.show(BottomSheetDialogType.POINT_TYPE)
    }

    fun onDone(start: RMMarker, end: RMMarker) {
        navigationController.setMarkers(start, end)
        back()
    }

    private fun listenPointType() {
        pointTypeController.state
            .listen {
                selectRoom = it == PointType.ROOM
                if (it  == PointType.MAP) {
                    if (isStartMarker) {
                        router?.navigateTo(
                            Screens.SelectPoint(
                                "start",
                                R.string.nav_a_title,
                                R.string.nav_a_desk,
                                "А",
                                "#F2514B",
                            )
                        )
                    } else {
                        router?.navigateTo(
                            Screens.SelectPoint(
                                "end",
                                R.string.nav_b_title,
                                R.string.nav_b_desk,
                                "Б",
                                "#636363",
                            )
                        )
                    }
                } else {
                    router?.navigateTo(Screens.SelectRoom)
                }
            }.connect()
    }

    private fun listenMarker() {
        selectMarkerController.state
            .listen {
                it.name = "Точка " + if (isStartMarker) "А" else "Б"
                navigationController.clearData()
                if (isStartMarker) {
                    viewState.addStartMarker(it)
                } else {
                    viewState.addEndMarker(it)
                }
            }.connect()
    }

    private fun listenRoom() {
            selectRoomController.state.listen {
                if (isStartMarker) {
                    viewState.addStartMarker(
                        RMMarker(
                            id = "start",
                            symbol = "A",
                            x = it.position.x.toFloat(),
                            y = it.position.y.toFloat(),
                            floor = it.position.floor,
                            color = Color.parseColor("#F2514B"),
                            textColor = Color.parseColor("#FFFFFF"),
                            name = it.name,
                        )
                    )
                } else {
                    viewState.addEndMarker(
                        RMMarker(
                            id = "end",
                            symbol = "Б",
                            x = it.position.x.toFloat(),
                            y = it.position.y.toFloat(),
                            floor = it.position.floor,
                            color = Color.parseColor("#636363"),
                            textColor = Color.parseColor("#FFFFFF"),
                            name = it.name,
                        )
                    )
                }
            }
    }

    fun back() {
        router?.exit()
    }
}