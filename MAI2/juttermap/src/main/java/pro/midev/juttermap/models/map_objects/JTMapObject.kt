package pro.midev.juttermap.models.map_objects

import pro.midev.juttermap.geometry.JTPoint

/**
 * Элемент списка с объектами.
 */
interface JTMapObject {
    /**
     * Объект карты должен возвращать
     * свои размеры.
     */
    fun getSize() : Pair<JTPoint, JTPoint>

    /**
     * Получение типа объекта.
     */
    fun getType() : String

    /**
     * Получение точек от которых зависит
     * положение объекта.
     */
    fun getListPoints() : MutableList<JTPoint>
}