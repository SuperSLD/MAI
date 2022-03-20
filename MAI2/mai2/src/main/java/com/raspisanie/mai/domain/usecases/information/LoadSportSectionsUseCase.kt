package com.raspisanie.mai.domain.usecases.information

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.NotificationsLocal
import com.raspisanie.mai.domain.models.SportLocal
import com.raspisanie.mai.domain.models.SportSectionLocal
import com.raspisanie.mai.domain.models.StudentOrganizationLocal
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toLocall
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadSportSectionsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<SportLocal> {
        val data = withIO { service.getSport() }
        return if (data.success) {
            data.data!!.toLocall()
        } else throw InvalidParameterException(data.message)
    }
}