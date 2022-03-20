package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.getAuthState
import com.raspisanie.mai.common.extesions.getNotifications

class GetNotificationsUseCase(
    private val context: Context,
) {

    operator fun invoke() = context.getNotifications()
}