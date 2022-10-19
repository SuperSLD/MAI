package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.saveEnabledTestMap

class SaveEnabledTestMapUseCase(
    private val context: Context,
) {

    operator fun invoke(enabled: Boolean) = context.saveEnabledTestMap(enabled)
}