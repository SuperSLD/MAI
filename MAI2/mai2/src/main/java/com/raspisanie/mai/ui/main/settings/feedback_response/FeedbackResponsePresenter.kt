package com.raspisanie.mai.ui.main.settings.feedback_response

import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.NotificationController
import com.raspisanie.mai.domain.usecases.main.LoadFeedbackResponseUseCase
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class FeedbackResponsePresenter : BasePresenter<FeedbackResponseView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val loadFeedbackResponsePresenter: LoadFeedbackResponseUseCase by inject()
    private val getNotificationsUseCase: GetNotificationsUseCase by inject()
    private val saveNotificationsUseCase: SaveNotificationsUseCase by inject()
    private val notificationController: NotificationController by inject()

    override fun attachView(view: FeedbackResponseView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenFeedbackResponse")
        loadList()
    }

    fun onNewQuestion() {
        router?.navigateTo(Screens.Feedback)
    }

    fun loadList() {
        val handler = createHandler {  viewState.showErrorLoading() }
        launchUI(handler) {
            viewState.toggleLoading(true)
            val list = withIO { loadFeedbackResponsePresenter() }
            hideNotifications()
            viewState.toggleLoading(false)
            viewState.showList(list)
        }
    }

    /**
     * Если загрузка прошла успешно пользователь
     * смог увидеть новые новости и можно скрыть
     * счетчик уведомлений.
     */
    private fun hideNotifications() {
        val notifications = getNotificationsUseCase()
        notifications.setSupportCount(0)
        saveNotificationsUseCase(notifications)
        notificationController.show(notifications)
    }

    fun back() = router?.exit()
}