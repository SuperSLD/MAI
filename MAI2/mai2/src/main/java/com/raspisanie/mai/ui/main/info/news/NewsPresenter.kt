package com.raspisanie.mai.ui.main.info.news

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.NotificationController
import com.raspisanie.mai.domain.usecases.information.news.LoadNewsUseCase
import com.raspisanie.mai.domain.usecases.information.news.SetLikeNewsUseCase
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.ui.main.info.news.NewsPagingParams.PAGE_SIZE
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val context: Context by inject()
    private val notificationController: NotificationController by inject()
    private val loadNewsUseCase: LoadNewsUseCase by inject()
    private val setLikeNewsUseCase: SetLikeNewsUseCase by inject()
    private val getNotificationsUseCase: GetNotificationsUseCase by inject()
    private val saveNotificationsUseCase: SaveNotificationsUseCase by inject()

    override fun attachView(view: NewsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenNews")
    }

    fun loadList(skip: Int) {
        launchUI(CoroutineExceptionHandler { _, thr ->
            viewState.showErrorLoading()
            context.showToast(R.drawable.ic_close_toast, thr.message.toString())
        }) {
            viewState.toggleLoading(true)
            val list = withIO { loadNewsUseCase(PAGE_SIZE, skip) }
            viewState.toggleLoading(false)
            hideNotifications()
            viewState.showList(list)
        }
    }

    fun like(id: String) {
        launchUI(CoroutineExceptionHandler { _, thr ->
            context.showToast(R.drawable.ic_close_toast, thr.message.toString())
        }) {
            val isLiked = withIO { setLikeNewsUseCase(id) }
            context.showToast(
                R.drawable.ic_like_toast,
                context.getString(if (isLiked) R.string.like_success else R.string.like_error)
            )
            if (isLiked) viewState.updateLike(id)
        }
    }

    /**
     * Если загрузка прошла успешно пользователь
     * смог увидеть новые новости и можно скрыть
     * счетчик уведомлений.
     */
    private fun hideNotifications() {
        val notifications = getNotificationsUseCase()
        notifications.setNewsCount(0)
        saveNotificationsUseCase(notifications)
        notificationController.show(notifications)
    }

    fun back() = router?.exit()
}

object NewsPagingParams {
    const val PAGE_SIZE = 20
}