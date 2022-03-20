package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.saveAuthState

class SaveAuthStateUseCase(
    private val context: Context,
) {

    operator fun invoke(authState: Boolean) = context.saveAuthState(authState)
}