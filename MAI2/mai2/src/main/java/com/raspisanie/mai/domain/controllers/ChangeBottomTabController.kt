package com.raspisanie.mai.domain.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import online.jutter.supersld.common.datacontrol.PublishDataController
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ChangeBottomTabController {
    val state = PublishDataController<SupportAppScreen>()

    fun changeMainScreen(screen: SupportAppScreen) = state.emit(screen)
}