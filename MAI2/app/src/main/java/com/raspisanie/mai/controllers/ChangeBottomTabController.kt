package com.raspisanie.mai.controllers

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ChangeBottomTabController {
    private val stateRelay = PublishRelay.create<SupportAppScreen>()

    val state: Observable<SupportAppScreen> = stateRelay
    fun changeMainScreen(screen: SupportAppScreen) = stateRelay.accept(screen)
}