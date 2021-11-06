package pro.midev.juttermap.server.callback

import pro.midev.juttermap.models.server.JTMapData

/**
 * Калбэк для загрузки списка карт.
 */
interface JTLoadMapsCallback {
    /**
     * Список карт успешно загружен.
     */
    fun onLoad(maps: MutableList<JTMapData>)

    /**
     * При загрузке списка произошла ошибка.
     */
    fun onError(err: String)
}