package com.raspisanie.mai.ui.main.settings.send_feedback

import android.content.Context
import android.text.InputType
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.enums.ToastType
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.controllers.ShowToastController
import com.raspisanie.mai.extesions.mappers.toFeedbackBody
import com.raspisanie.mai.server.ApiService
import com.yandex.metrica.YandexMetrica
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import online.juter.supersld.view.input.form.JTForm
import online.juter.supersld.view.input.form.JTFormPage
import online.juter.supersld.view.input.form.lines.TextInputLine
import online.juter.supersld.view.input.form.lines.TextLine
import org.koin.core.inject
import pro.midev.supersld.common.base.BasePresenter
import timber.log.Timber

@InjectViewState
class SendFeedbackPresenter : BasePresenter<SendFeedbackView>() {

    private val showToastController: ShowToastController by inject()
    private val service: ApiService by inject()
    private val context: Context by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: SendFeedbackView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSendFeedback")
    }

    fun sendFeedback(form: JTForm) {
        service.sendFeedback(form.toFeedbackBody())
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
                    router?.replaceScreen(Screens.Success)
                }
                .subscribe()
                .connect()
    }

    fun createForm() = JTForm(
        pages = mutableListOf(
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Укажите ваши контактные данные для того, чтобы мы смогли с вами связаться в случае, если понадобится дополнительная информация для решения проблеммы"),
                    TextInputLine("name", "Как к вам обращаться?", mandatory = true),
                    TextInputLine("email", "Ваш E-mail", mandatory = true)
                ),
                buttonText = "Далее"
            ),
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Опишите вашу проблему как можно подробнее, тогда мы сможем ее решить"),
                    TextInputLine("message", "Информация о проблемме",
                        mandatory = true, inputType = TextInputLine.TEXT_MULTILINE, minLines = 6
                    )
                ),
                buttonText = "Далее"
            )
        ),
        finishText = "Отправть сообщение"
    )

    fun back() = router?.exit()
}