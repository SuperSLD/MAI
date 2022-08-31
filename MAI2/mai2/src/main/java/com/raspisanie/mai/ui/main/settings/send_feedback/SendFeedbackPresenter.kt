package com.raspisanie.mai.ui.main.settings.send_feedback

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.enums.ToastType
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.controllers.ShowToastController
import com.raspisanie.mai.domain.usecases.main.SendFeedbackUseCase
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.juter.supersld.view.input.form.JTForm
import online.juter.supersld.view.input.form.JTFormPage
import online.juter.supersld.view.input.form.lines.TextInputLine
import online.juter.supersld.view.input.form.lines.TextLine
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class SendFeedbackPresenter : BasePresenter<SendFeedbackView>() {

    private val showToastController: ShowToastController by inject()
    private val context: Context by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val sendFeedbackUseCase: SendFeedbackUseCase by inject()

    override fun attachView(view: SendFeedbackView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenSendFeedback")
    }

    fun sendFeedback(form: JTForm) {
        val handler = createHandler {
            viewState.toggleLoading(false)
            showToastController.show(ToastType.ERROR, context.getString(R.string.errorLoading))
        }
        launchUI(handler) {
            withIO { sendFeedbackUseCase(form) }
            router?.replaceScreen(Screens.Success)
        }
    }

    fun createForm() = JTForm(
        pages = mutableListOf(
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Укажите ваши контактные данные для того, чтобы мы смогли с вами связаться в случае, если понадобится дополнительная информация для решения проблеммы"),
                    TextInputLine("name", "Как к вам обращаться?", mandatory = true),
                    TextInputLine("email", "Ссылка на vk или tg", mandatory = true)
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
        finishText = "Отправить сообщение"
    )

    fun back() = router?.exit()
}