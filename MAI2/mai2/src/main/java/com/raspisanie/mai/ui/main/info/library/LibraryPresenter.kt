package com.raspisanie.mai.ui.main.info.library

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.mappers.toLocall
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class LibraryPresenter : BasePresenter<LibraryView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val service: ApiService by inject()

    override fun attachView(view: LibraryView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenLibrary")
        loadList()
    }

    fun loadList() {
        service.getLibrary()
            .map { if (it.success) it.data else error(it.message.toString()) }
            .map { it!!.toLocall() }
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