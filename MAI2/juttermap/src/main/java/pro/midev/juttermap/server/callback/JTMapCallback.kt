package pro.midev.juttermap.server.callback

import pro.midev.juttermap.models.map_objects.JTMap

/**
 * Калбэк для загрузки списка карт.
 */
interface JTMapCallback {
    /**
     * Список карт успешно загружен.
     */
    fun onLoad(map: JTMap)

    /**
     * При загрузке списка произошла ошибка.
     */
    fun onError(err: String)
}