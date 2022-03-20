package com.raspisanie.mai.domain.usecases.information.news

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.*
import com.raspisanie.mai.extesions.mappers.toLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadNewsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(limit: Int, skip: Int): MutableList<NewsLocal> {
        val data = withIO { service.getNews(limit, skip) }
        return if (data.success) {
            data.data!!.map { it.toLocal() }.toMutableList()
        } else throw InvalidParameterException(data.message)
    }
}