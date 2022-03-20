package com.raspisanie.mai.domain.usecases.information

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.NotificationsLocal
import com.raspisanie.mai.domain.models.StudentOrganizationLocal
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadStudOrgUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<StudentOrganizationLocal> {
        val data = withIO { service.getStudents() }
        return if (data.success) {
            data.data!!.toLocal()
        } else throw InvalidParameterException(data.message)
    }
}