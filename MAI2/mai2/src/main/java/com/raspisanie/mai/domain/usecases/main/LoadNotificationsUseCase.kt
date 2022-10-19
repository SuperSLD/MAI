package com.raspisanie.mai.domain.usecases.main

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.NotificationsLocal
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadNotificationsUseCase(
    private val service: ApiService,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val saveNotificationsUseCase: SaveNotificationsUseCase,
) {

    suspend operator fun invoke(): NotificationsLocal {
        val oldData = getNotificationsUseCase()
        val data = withIO { service.getNotifications(oldData.lastUpdate) }
        return if (data.success) with(data.data!!) {
            oldData.setNewsCount(this.newsCount + oldData.getNewsCount())
            oldData.setSupportCount(this.supportResponseCount + oldData.getSupportCount())
            oldData.updateDate()
            saveNotificationsUseCase(oldData)
            oldData
        } else throw InvalidParameterException(data.message)
    }
}