package com.raspisanie.mai.di

import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.domain.controllers.*
import org.koin.core.module.Module

fun Module.provideUeeCases() {
    //single { LoadPromoPageUseCase(get()) }

}

fun Module.provideControllers() {
    single { BottomSheetDialogController() }
    single { BottomVisibilityController() }
    single { ChangeBottomTabController() }
    single { ConfirmController() }
    single { SelectWeekController() }
    single { ShowToastController() }
    single { NotificationController() }
    single { SelectDateController() }
}
