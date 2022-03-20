package com.raspisanie.mai.domain.usecases.information.lector

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.models.TeacherLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class SearchLectorsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(name: String): MutableList<TeacherLocal> {
        val data = withIO { service.getSearchLector(name) }
        return if (data.success) {
            data.data!!.map { it.toLocal() }.toMutableList()
        } else throw InvalidParameterException(data.message)
    }
}