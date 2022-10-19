package com.raspisanie.mai.di

import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.domain.controllers.*
import com.raspisanie.mai.domain.usecases.devs.GetAllDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.LoadDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.SaveInRealmDevsUseCase
import com.raspisanie.mai.domain.usecases.groups.*
import com.raspisanie.mai.domain.usecases.information.*
import com.raspisanie.mai.domain.usecases.information.adv.CreateAdvUseCase
import com.raspisanie.mai.domain.usecases.information.adv.LoadAdvUseCase
import com.raspisanie.mai.domain.usecases.information.adv.SetLikeAdvUseCase
import com.raspisanie.mai.domain.usecases.information.lector.LoadLectorScheduleUseCase
import com.raspisanie.mai.domain.usecases.information.lector.SearchLectorsUseCase
import com.raspisanie.mai.domain.usecases.information.news.LoadNewsUseCase
import com.raspisanie.mai.domain.usecases.information.news.SetLikeNewsUseCase
import com.raspisanie.mai.domain.usecases.main.LoadFeedbackResponseUseCase
import com.raspisanie.mai.domain.usecases.main.LoadNotificationsUseCase
import com.raspisanie.mai.domain.usecases.main.RemoveRealmDataUseCase
import com.raspisanie.mai.domain.usecases.main.SendFeedbackUseCase
import com.raspisanie.mai.domain.usecases.schedule.*
import com.raspisanie.mai.domain.usecases.state.*
import org.koin.core.module.Module

fun Module.provideUeeCases() {
    single { GroupIsSelectedUseCase(get()) }
    single { GetCurrentGroupUseCase(get()) }
    single { SearchGroupsUseCase(get()) }
    single { SaveAuthStateUseCase(get()) }
    single { GetAuthStateUseCase(get()) }
    single { GetNotificationsUseCase(get()) }
    single { GetSemesterUseCase(get()) }
    single { GetThemeIsDayUseCase(get()) }
    single { SaveNotificationsUseCase(get()) }
    single { SaveThemeIsDayUseCase(get()) }
    single { SaveSemesterUseCase(get()) }
    single { UpdateGroupUseCase(get()) }
    single { LoadNotificationsUseCase(get(), get(), get()) }
    single { GetStorageScheduleUseCase(get()) }
    single { LoadScheduleUseCase(get()) }
    single { ClearScheduleForCurrentGroupUseCase(get()) }
    single { UpdateCurrentGroupScheduleUseCase(get(), get()) }
    single { RemoveRealmDataUseCase(get()) }
    single { GetAllStorageSchedulesUseCase(get()) }
    single { GetAllGroupsUseCase(get()) }
    single { GetAllDevsUseCase(get()) }
    single { LoadDevsUseCase(get()) }
    single { SaveInRealmDevsUseCase(get()) }
    single { SendFeedbackUseCase(get()) }
    single { LoadStudOrgUseCase(get()) }
    single { LoadSportSectionsUseCase(get()) }
    single { SearchLectorsUseCase(get()) }
    single { LoadLectorScheduleUseCase(get()) }
    single { LoadNewsUseCase(get()) }
    single { SetLikeNewsUseCase(get()) }
    single { LoadAdvUseCase(get()) }
    single { SetLikeAdvUseCase(get()) }
    single { CreateAdvUseCase(get()) }
    single { LoadCanteensUseCase(get()) }
    single { LoadCreativeGroupsUseCase(get()) }
    single { LoadLibraryUseCase(get()) }
    single { LoadFeedbackResponseUseCase(get()) }
    single { TestMapIsEnabledUseCase(get()) }
    single { SaveEnabledTestMapUseCase(get()) }
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
    single { NavigationController() }
    single { SelectRoomController() }
    single { SelectMarkerController() }
    single { PointTypeController() }
}
