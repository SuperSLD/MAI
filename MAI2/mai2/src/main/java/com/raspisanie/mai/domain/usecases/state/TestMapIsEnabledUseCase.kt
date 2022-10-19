package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.getEnabledTestMap

class TestMapIsEnabledUseCase(
    private val context: Context,
) {

    operator fun invoke() = context.getEnabledTestMap()
}