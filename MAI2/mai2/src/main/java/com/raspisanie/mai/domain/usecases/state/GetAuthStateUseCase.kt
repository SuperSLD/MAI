package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.getAuthState

class GetAuthStateUseCase(
    private val context: Context,
) {

    operator fun invoke() = context.getAuthState()
}