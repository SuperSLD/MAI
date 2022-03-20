package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.getIsDayTheme

class GetThemeIsDayUseCase(
    private val context: Context,
) {

    operator fun invoke() = context.getIsDayTheme()
}