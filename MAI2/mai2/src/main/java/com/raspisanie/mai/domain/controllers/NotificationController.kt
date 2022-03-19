package com.raspisanie.mai.domain.controllers

import com.jakewharton.rxrelay3.BehaviorRelay
import com.raspisanie.mai.domain.models.NotificationsLocal
import io.reactivex.rxjava3.core.Observable

class NotificationController {
    private val stateRelay = BehaviorRelay.create<NotificationsLocal>()

    val state: Observable<NotificationsLocal> = stateRelay
    fun show(notifications: NotificationsLocal) = stateRelay.accept(notifications)
}