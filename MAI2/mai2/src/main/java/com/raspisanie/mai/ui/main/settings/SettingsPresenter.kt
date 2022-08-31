package com.raspisanie.mai.ui.main.settings

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.ConfirmController
import com.raspisanie.mai.domain.usecases.devs.GetAllDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.LoadDevsUseCase
import com.raspisanie.mai.domain.usecases.devs.SaveInRealmDevsUseCase
import com.raspisanie.mai.domain.usecases.groups.GetAllGroupsUseCase
import com.raspisanie.mai.domain.usecases.groups.GetCurrentGroupUseCase
import com.raspisanie.mai.domain.usecases.schedule.GetAllStorageSchedulesUseCase
import com.raspisanie.mai.domain.usecases.state.GetThemeIsDayUseCase
import com.raspisanie.mai.domain.usecases.state.SaveThemeIsDayUseCase
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject
import timber.log.Timber

@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val getCurrentGroupUseCase: GetCurrentGroupUseCase by inject()
    private val getAllStorageSchedulesUseCase: GetAllStorageSchedulesUseCase by inject()
    private val getAllGroupUseCase: GetAllGroupsUseCase by inject()
    private val saveThemeIsDayUseCase: SaveThemeIsDayUseCase by inject()

    private val context: Context by inject()

    private var lastDeletedGroup: GroupRealm? = null
    private val confirmController: ConfirmController by inject()
    private val bottomSheetDialogController: BottomSheetDialogController by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSettings")
    }

    override fun attachView(view: SettingsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        showCurrentGroup()
        showGroupsList()
        showScheduleInfo()
        listenConfirm()
    }

    fun onSaveTheme(isDay: Boolean) {
        saveThemeIsDayUseCase(isDay)
    }

    fun select(group: GroupRealm) {
        YandexMetrica.reportEvent("SelectGroupInSettings")
        Timber.d(group.id)
        context.showToast(R.drawable.ic_people_toast, context.getString(R.string.settings_change_group))
        showCurrentGroup()
    }

    fun remove(group: GroupRealm) {
        lastDeletedGroup = group
        bottomSheetDialogController.show(
                BottomSheetDialogType.CONFIRM,
                Pair(
                        context.getString(R.string.settings_group_confirm_delete),
                        context.getString(R.string.settings_group_confirm_delete_accept)
                )
        )
    }

    private fun showCurrentGroup() {
        viewState.showCurrentGroup(getCurrentGroupUseCase())
    }

    private fun showScheduleInfo() {
        viewState.showScheduleInfo(getAllStorageSchedulesUseCase(), getAllGroupUseCase())
    }

    private fun listenConfirm() {
        confirmController.state
            .listen { confirm ->
                if (confirm) {
                    context.showToast(R.drawable.ic_close_toast, context.getString(R.string.settings_remove_group))
                    lastDeletedGroup?.let { it -> viewState.removeGroup(it) }
                    lastDeletedGroup = null
                    showScheduleInfo()
                }
            }.connect()
    }

    private fun showGroupsList() {
        viewState.showGroups(getAllGroupUseCase())
    }

    fun sendFeedback() = router?.navigateTo(Screens.Feedback)

    fun addGroup() = router?.navigateTo(Screens.AddGroup)

    fun onOpenAbout() = router?.navigateTo(Screens.About)

    fun back() {
        router?.exit()
    }
}