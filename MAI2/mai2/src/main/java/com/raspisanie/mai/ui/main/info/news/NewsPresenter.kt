package com.raspisanie.mai.ui.main.info.news

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.NotificationController
import com.raspisanie.mai.extesions.getNotifications
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.saveNotifications
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.ui.main.info.news.NewsPagingParams.PAGE_SIZE
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val service: ApiService by inject()
    private val context: Context by inject()
    private val notificationController: NotificationController by inject()

    override fun attachView(view: NewsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenNews")
        //loadList(0)
    }

    fun loadList(skip: Int) {
        service.getNews(PAGE_SIZE, skip)
            .map { if (it.success) it.data else error(it.message.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                it.printStackTrace()
                viewState.showErrorLoading()
                context.showToast(R.drawable.ic_close_toast, it.message.toString())
            }
            .subscribe(
                {
                    hideNotifications()

                    viewState.showList(it!!.map { n->n.toLocal() }.toMutableList())
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun like(id: String) {
        service.likeNews(id)
            .map { if (it.success) it.data else error(it.message.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                it.printStackTrace()
                viewState.showErrorLoading()
                context.showToast(R.drawable.ic_close_toast, it.message.toString())
            }
            .subscribe(
                {
                    context.showToast(
                        R.drawable.ic_like_toast,
                        context.getString(if (it!!) R.string.like_success else R.string.like_error)
                    )
                    if (it) viewState.updateLike(id)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    /**
     * Если загрузка прошла успешно пользователь
     * смог увидеть новые новости и можно скрыть
     * счетчик уведомлений.
     */
    private fun hideNotifications() {
        val notifications = context.getNotifications()
        notifications.setNewsCount(0)
        context.saveNotifications(notifications)
        notificationController.show(notifications)
    }

    fun back() = router?.exit()
}

object NewsPagingParams {
    const val PAGE_SIZE = 20
}