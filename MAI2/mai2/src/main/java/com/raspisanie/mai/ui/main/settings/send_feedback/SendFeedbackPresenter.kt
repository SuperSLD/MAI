package com.raspisanie.mai.ui.main.settings.send_feedback

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.common.enums.ToastType
import com.raspisanie.mai.controllers.ShowToastController
import com.raspisanie.mai.models.server.FeedbackBody
import com.raspisanie.mai.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class SendFeedbackPresenter : BasePresenter<SendFeedbackView>() {

    private val showToastController: ShowToastController by inject()
    private val service: ApiService by inject()
    private val context: Context by inject()

    fun sendFeedback(
            name: String,
            email: String,
            message: String
    ) {
        service.sendFeedback(FeedbackBody(
                name = name,
                email = email,
                message = message
        ))
                .map { if (it.success) 0 else error(it.message.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnError {
                    viewState.toggleLoading(false)
                    showToastController.show(ToastType.ERROR, context.getString(R.string.errorLoading))
                    Timber.e(it)
                }
                .doOnSuccess {
                    back()
                }
                .subscribe()
                .connect()
    }

    fun back() = router?.exit()
}