package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.saveIsDayTheme
import com.raspisanie.mai.common.extesions.saveNotifications
import com.raspisanie.mai.common.extesions.saveSemester
import com.raspisanie.mai.domain.models.NotificationsLocal

class SaveNotificationsUseCase(
    private val context: Context,
) {

    operator fun invoke(notifications: NotificationsLocal) = context.saveNotifications(notifications)
}