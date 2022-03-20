package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.saveIsDayTheme

class SaveThemeIsDayUseCase(
    private val context: Context,
) {

    operator fun invoke(isDay: Boolean) = context.saveIsDayTheme(isDay)
}