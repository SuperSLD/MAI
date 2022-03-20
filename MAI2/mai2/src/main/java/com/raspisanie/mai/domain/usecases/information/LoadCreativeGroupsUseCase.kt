package com.raspisanie.mai.domain.usecases.information

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.CreativeLocal
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadCreativeGroupsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<CreativeLocal> {
        val data = withIO { service.getCreative() }
        return if (data.success) {
            data.data!!.toLocal()
        } else throw InvalidParameterException(data.message)
    }
}