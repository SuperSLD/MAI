package com.raspisanie.mai.domain.usecases.information.adv

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.*
import com.raspisanie.mai.extesions.mappers.toAdsCreateBody
import com.raspisanie.mai.extesions.mappers.toLocal
import online.juter.supersld.view.input.form.JTForm
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class CreateAdvUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(form: JTForm) {
        val data = withIO { service.createAdv(form.toAdsCreateBody()) }
        if (!data.success) throw InvalidParameterException(data.message)
    }
}