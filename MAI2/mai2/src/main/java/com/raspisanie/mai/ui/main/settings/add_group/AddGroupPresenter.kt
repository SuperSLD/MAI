package com.raspisanie.mai.ui.main.settings.add_group

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.getCurrentGroup
import com.raspisanie.mai.extesions.realm.updateGroup
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.ui.select_group.select_group.SelectGroupView
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class AddGroupPresenter : BasePresenter<SelectGroupView>() {

    private val service: ApiService by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private var lastSearch = ""
    private val realm: Realm by inject()

    override fun attachView(view: SelectGroupView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAddGroup")
    }

    fun select(group: GroupRealm) {
        if (realm.getCurrentGroup()?.id != group.id) {
            group.selected = false
            YandexMetrica.reportEvent("AddGroupSuccess")
            realm.updateGroup(group)
        }
        back()
    }

    fun search(name: String = lastSearch) {
        lastSearch = if (name.isEmpty()) "#" else name
        service.getGroupList(lastSearch)
                .map { if (it.success) it.data else error(it.message.toString()) }
                .map { it!!.toRealm() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnError {
                    it.printStackTrace()
                    viewState.showErrorLoading()
                }
                .subscribe(
                        {
                            viewState.toggleLoading(false)
                            viewState.showList(it)
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun next() {
        router?.newRootScreen(Screens.FlowMain)
    }

    fun back() = router?.exit()
}