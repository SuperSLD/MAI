package com.raspisanie.mai.domain.usecases.information.adv

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.*
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class SetLikeAdvUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(id: String): Boolean {
        val data = withIO { service.likeAdv(id) }
        return if (data.success) {
            data.data!!
        } else throw InvalidParameterException(data.message)
    }
}