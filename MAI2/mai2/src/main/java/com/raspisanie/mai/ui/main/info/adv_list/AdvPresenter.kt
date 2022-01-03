package com.raspisanie.mai.ui.main.info.adv_list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
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
import ru.terrakok.cicerone.Screen
import timber.log.Timber

@InjectViewState
class AdvPresenter : BasePresenter<AdvView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val service: ApiService by inject()

    override fun attachView(view: AdvView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAdvList")
        loadList(0)
    }

    fun loadList(skip: Int) {
        service.getAdvPage(AdvPagingParams.PAGE_SIZE, skip)
            .map { if (it.success) it.data else error(it.message.toString()) }
            .map { it!!.toLocal() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                it.printStackTrace()
                viewState.showErrorLoading()
            }
            .subscribe(
                {
                    viewState.addList(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun addAdv() {
        router?.navigateTo(Screens.AddAdv)
    }

    fun back() = router?.exit()
}

object AdvPagingParams {
    const val PAGE_SIZE = 20
}