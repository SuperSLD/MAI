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
import com.raspisanie.mai.models.server.FeedbackBody
import com.raspisanie.mai.server.ApiService
import com.raspisanie.mai.ui.main.info.adv_list.add_adv.AddAdvView
import com.raspisanie.mai.ui.view.form.Form
import com.raspisanie.mai.ui.view.form.FormPage
import com.raspisanie.mai.ui.view.form.lines.TextInputLine
import com.raspisanie.mai.ui.view.form.lines.TextLine
import com.yandex.metrica.YandexMetrica
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
    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: SendFeedbackView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSendFeedback")
    }

    fun sendFeedback(form: Form) {
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

    fun createForm() = Form(
        pages = mutableListOf(
            FormPage(
                lines = mutableListOf(
                    TextLine("Укажите ваши контактные данные для того, чтобы мы смогли с вами связаться в случае, если понадобится дополнительная информация для решения проблеммы"),
                    TextInputLine("name", "name", "Как к вам обращаться?", mandatory = true),
                    TextInputLine("email", "email", "Ваш E-mail", mandatory = true)
                ),
                buttonText = "Далее"
            ),
            FormPage(
                lines = mutableListOf(
                    TextLine("Опишите вашу проблему как можно подробнее, тогда мы сможем ее решить"),
                    TextInputLine("message", "messafe", "Информация о проблемме",
                        mandatory = true, inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE, minLines = 6
                    )
                ),
                buttonText = "Далее"
            )
        ),
        finishText = "Отправть сообщение"
    )

    fun back() = router?.exit()
}