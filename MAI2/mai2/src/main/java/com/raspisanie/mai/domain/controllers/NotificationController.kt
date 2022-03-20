package com.raspisanie.mai.domain.controllers

import com.raspisanie.mai.domain.models.NotificationsLocal
import online.jutter.supersld.common.datacontrol.BehaviorDataController

class NotificationController {
    val state = BehaviorDataController<NotificationsLocal>()

    fun show(notifications: NotificationsLocal) = state.emit(notifications)
}