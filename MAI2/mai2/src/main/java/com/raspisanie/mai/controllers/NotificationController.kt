package com.raspisanie.mai.controllers

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import com.raspisanie.mai.models.local.NotificationsLocal
import io.reactivex.rxjava3.core.Observable
import ru.terrakok.cicerone.android.support.SupportAppScreen

class NotificationController {
    private val stateRelay = BehaviorRelay.create<NotificationsLocal>()

    val state: Observable<NotificationsLocal> = stateRelay
    fun show(notifications: NotificationsLocal) = stateRelay.accept(notifications)
}