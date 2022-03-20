package com.raspisanie.mai.domain.usecases.groups

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.models.GroupLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class SearchGroupsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(group: String): MutableList<GroupLocal>? {
        val data = withIO { service.getGroupList(group) }
        return if (data.success) {
            data.data?.map { it.toLocal() }?.toMutableList()
        } else throw InvalidParameterException(data.message)
    }
}