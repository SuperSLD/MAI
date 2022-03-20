package com.raspisanie.mai.ui.main.info.adv_list.add_adv

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.adv.CreateAdvUseCase
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.ui.ext.createHandler
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.CoroutineExceptionHandler
import online.juter.supersld.view.input.form.JTForm
import online.juter.supersld.view.input.form.JTFormPage
import online.juter.supersld.view.input.form.lines.SolidTextLine
import online.juter.supersld.view.input.form.lines.TextInputLine
import online.juter.supersld.view.input.form.lines.TextLine
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class AddAdvPresenter : BasePresenter<AddAdvView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val createAdvUseCase: CreateAdvUseCase by inject()
    private val context: Context by inject()

    override fun attachView(view: AddAdvView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAdvCreate")
    }

    // создаем форму
    fun createForm() = JTForm(
        pages = mutableListOf(
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Для начала необходимо указать свое имя и фамилию, чтоб все, кто смотрит на объявление, знали к кому обращаться"),
                    TextInputLine("name", "Имя", mandatory = true),
                    TextInputLine("lastname",  "Фамилия", mandatory = true)
                ),
                buttonText = "Перейти к описанию"
            ),
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Напишите текст для вашего объявления. После отправки мы его проверим и допустим в общую ленту"),
                    TextInputLine("text", "Текст объявления",
                        mandatory = true, inputType = TextInputLine.TEXT_MULTILINE, minLines = 6
                    )
                ),
                buttonText = "Указать ссылки"
            ),
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Отлично! Остался последний шаг! Вам нужно указать как с вами можно связаться"),
                    TextLine("Вы можете заполнить несколько ссылкок или только одну"),
                    TextInputLine("vk", "Ссылка на страницу вк или на группу", mandatory = false),
                    TextInputLine("tg", "Ссылка на ваш телеграмм, канал или чат", mandatory = false),
                    TextInputLine("other", "Ссылка на любой другой ресурс", mandatory = false),
                    SolidTextLine("Важно понимать, что ссылки разделены именно так из за иконок, и если их перемешать, то вы просто запутаете пользователя"),
                ),
                buttonText = "Далее"
            )
        ),
        finishText = "Создать объявление"
    )


    fun sendForm(form: JTForm) {
        val handler = createHandler {
            context.showToast(
                R.drawable.ic_report_gmailerrorred,
                it.message.toString(),
                true
            )
            viewState.toggleLoading(false)
        }
        launchUI(handler) {
            viewState.toggleLoading(true)
            withIO { createAdvUseCase(form) }
            router?.replaceScreen(Screens.CreateAdvSuccess)
        }
    }

    fun back() = router?.exit()
}