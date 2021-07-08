package com.raspisanie.mai.ui.main.info.news

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.NotificationController
import com.raspisanie.mai.extesions.getNotifications
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toLocall
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.extesions.realm.addNews
import com.raspisanie.mai.extesions.realm.getNews
import com.raspisanie.mai.extesions.saveNotifications
import com.raspisanie.mai.extesions.toRealmList
import com.raspisanie.mai.server.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val service: ApiService by inject()
    private val context: Context by inject()
    private val realm: Realm by inject()
    private val notificationController: NotificationController by inject()

    override fun attachView(view: NewsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenNews")
        loadList()
    }

    fun loadList() {
        service.getNews()
            .map { if (it.success) it.data else error(it.message.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleLoading(true) }
            .doOnError {
                it.printStackTrace()
                val news = realm.getNews()
                if (news.size == 0) {
                    viewState.showErrorLoading()
                } else {
                    viewState.toggleLoading(false)
                    viewState.showList(news)
                }
            }
            .subscribe(
                {
                    /*
                    Если загрузка прошла успешно пользователь
                    смог увидеть новые новости и можно скрыть
                    счетчик уведомлений.
                     */
                    val notifications = context.getNotifications()
                    notifications.setNewsCount(0)
                    context.saveNotifications(notifications)
                    notificationController.show(notifications)

                    val news = it!!.map { i -> i.toRealm() }.toRealmList()
                    viewState.toggleLoading(false)
                    realm.addNews(news)
                    viewState.showList(news.map { n->n.toLocal() }.toMutableList())
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }
    fun back() = router?.exit()
}