package pro.midev.juttermap.common

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import pro.midev.juttermap.geometry.JTPoint
import pro.midev.juttermap.models.map_objects.JTMap
import pro.midev.juttermap.models.map_objects.JTMapObject
import pro.midev.juttermap.models.map_objects.objects.JTBuilding

/**
 * Фабрика объектов карты.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
object JTMapObjectsFactory {

    /**
     * Создаем объекты карты из json
     * списка, приходящего с сервера.
     *
     * @param obj объект json.
     */
    fun create(obj: JsonObject) : JTMapObject? {
        return when(obj["type"].asString) {
            JTMap.TYPE_BUILDING -> JTBuilding(obj)
            else -> null
        }
    }

    /**
     * Создание списка объектов
     * карты.
     * И поиск геометрических размеров карты.
     */
    fun create(array: JsonArray) : Pair<MutableList<JTMapObject>, Pair<JTPoint, JTPoint>> {
        var maxLat = -2000.0
        var maxLon = -2000.0
        var minLat = 2000.0
        var minLon = 2000.0

        val objects = mutableListOf<JTMapObject>()
        for (i in 0 until array.size()) {
            val obj = create(array.get(i).asJsonObject)
            if (obj != null) {
                val size = obj.getSize()
                if (size.first.latitude < minLat) minLat = size.first.latitude
                if (size.first.longitude < minLon) minLon = size.first.longitude
                if (size.second.latitude > maxLat) maxLat = size.second.latitude
                if (size.second.longitude > maxLon) maxLon = size.second.longitude
                objects.add(obj)
            }
        }
        return Pair(objects, Pair(JTPoint(minLat, minLon), JTPoint(maxLat, maxLon)))
    }
}