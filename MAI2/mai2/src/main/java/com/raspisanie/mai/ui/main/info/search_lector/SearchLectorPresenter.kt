package com.raspisanie.mai.ui.main.info.search_lector

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.domain.models.TeacherLocal
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class SearchLectorPresenter : BasePresenter<SearchLectorView>() {

    private val service: ApiService by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private var lastSearch = ""

    override fun attachView(view: SearchLectorView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSearchLector")
    }

    fun select(teacher: TeacherLocal) {
        YandexMetrica.reportEvent("LectorSelected")
        router?.navigateTo(Screens.LectorSchedule(teacher))
    }

    fun search(name: String = lastSearch) {
        lastSearch = name.ifEmpty { "#" }
        service.getSearchLector(lastSearch)
                .map { if (it.success) it.data else error(it.message.toString()) }
                .map { it!!.map { t -> t.toLocal() }.toMutableList() }
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