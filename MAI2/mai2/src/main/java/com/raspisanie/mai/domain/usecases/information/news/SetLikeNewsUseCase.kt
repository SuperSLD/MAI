package com.raspisanie.mai.domain.usecases.information.news

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.*
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class SetLikeNewsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(id: String): Boolean {
        val data = withIO { service.likeNews(id) }
        return if (data.success) {
            data.data!!
        } else throw InvalidParameterException(data.message)
    }
}