package com.raspisanie.mai.ui.main.settings

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.ConfirmController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.realm.*
import com.raspisanie.mai.models.realm.GroupRealm
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Screen
import timber.log.Timber

@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val realm: Realm by inject()
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

    fun select(group: GroupRealm) {
        YandexMetrica.reportEvent("SelectGroupInSettings")
        Timber.d(group.id)
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
        realm.getCurrentGroup()?.let { viewState.showCurrentGroup(it) }
    }

    private fun showScheduleInfo() {
        viewState.showScheduleInfo(realm.getAllSchedules().toLocal(), realm.getAllGroup())
    }

    private fun listenConfirm() {
        confirmController.state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { confirm ->
                            if (confirm) {
                                lastDeletedGroup?.let { it -> viewState.removeGroup(it) }
                                lastDeletedGroup = null
                                showScheduleInfo()
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    private fun showGroupsList() {
        viewState.showGroups(realm.getAllGroup())
    }

    fun sendFeedback() = router?.navigateTo(Screens.Feedback)

    fun addGroup() = router?.navigateTo(Screens.AddGroup)

    fun back() {
        router?.exit()
    }
}