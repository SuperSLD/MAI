package com.raspisanie.mai.ui.main.info.canteens

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.server.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class CanteensPresenter : BasePresenter<CanteensView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val service: ApiService by inject()

    override fun attachView(view: CanteensView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenCanteens")
        loadList()
    }

    fun loadList() {
        service.getCanteens()
            .map { if (it.success) it.data else error(it.message.toString()) }
            .map { it!!.toLocal() }
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

    fun back() = router?.exit()
}