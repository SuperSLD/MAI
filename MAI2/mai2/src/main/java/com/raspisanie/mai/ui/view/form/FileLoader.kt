package com.raspisanie.mai.ui.view.form

/**
 * Загрузчик файлов для линии.
 * Отправка и пик файла на устройстве производится
 * с использованием контекста активити, передавать
 * его в холдер не безопасно.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
abstract class FileLoader {

    private var loadStack = hashMapOf<String ,(Boolean)->Unit>()
    fun onLoadChangeState(id: String, listener: (Boolean)->Unit) {
        this.loadStack[id] = listener
    }

    /**
     * (String, String) -> первый параметр name, второй параметр результат загрузки
     */
    private var finishStack = hashMapOf<String, (String, String) -> Unit>()
    fun onFinishLoading(id: String, listener: (String, String)->Unit) {
        finishStack[id] = listener
    }

    /**
     * Инициализация загрузки файла.
     * @param id ключ для определения принадлежности результата.
     */
    abstract fun pickFile(id: String, data: Any? = null)

    /**
     * Отображение загрузки.
     *
     * @param show показать загрузку
     */
    fun toggleLoading(id: String, show: Boolean) {
        loadStack[id]?.let { it(show) }
    }

    /**
     * Завершение загрузки.
     * @param id входной ключ.
     * @param result результат загрузки (название или id файла где-то)
     */
    fun finishLoading(id: String, name: String, result: String) {
        finishStack[id]?.let { it(name, result) }
    }

    /**
     * Удаление связи с слушателем
     * по указанному ключу.
     *
     * @param id ключ объекта слушателя.
     */
    fun disconnect(id: String) {
        loadStack.remove(id)
        finishStack.remove(id)
    }
}