package com.raspisanie.mai.domain.usecases.state

import android.content.Context
import com.raspisanie.mai.common.extesions.saveIsDayTheme
import com.raspisanie.mai.common.extesions.saveSemester

class SaveSemesterUseCase(
    private val context: Context,
) {

    operator fun invoke(sem: Int) = context.saveSemester(sem)
}