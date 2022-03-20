package com.raspisanie.mai.domain.usecases.information

import com.raspisanie.mai.data.db.models.CanteenLocal
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadCanteensUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<CanteenLocal> {
        val data = withIO { service.getCanteens() }
        return if (data.success) {
            data.data!!.toLocal()
        } else throw InvalidParameterException(data.message)
    }
}