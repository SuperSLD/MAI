package com.raspisanie.mai.domain.usecases.information

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.LibraryLocal
import com.raspisanie.mai.extesions.mappers.toLocall
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadLibraryUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<LibraryLocal> {
        val data = withIO { service.getLibrary() }
        return if (data.success) {
            data.data!!.toLocall()
        } else throw InvalidParameterException(data.message)
    }
}