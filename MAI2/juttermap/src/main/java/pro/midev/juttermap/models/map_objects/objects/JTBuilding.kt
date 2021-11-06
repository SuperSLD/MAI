package pro.midev.juttermap.models.map_objects.objects

import com.google.gson.JsonObject
import pro.midev.juttermap.geometry.JTPoint
import pro.midev.juttermap.models.map_objects.JTMap
import pro.midev.juttermap.models.map_objects.JTMapObject

class JTBuilding(json: JsonObject): JTMapObject {
    val id: String = json["id"].asString
    val name: String = json["name"].asString
    val address: String = json["address"].asString
    val buildingType: String = json["buildingType"].asString
    val points: MutableList<JTPoint> = json["points"].asJsonArray.map { JTPoint(it.asJsonObject) }.toMutableList()

    companion object {
        const val CAMPUS = "campus"
        const val NON_RESIDENTIAL = "non-residential"
        const val RESIDENTIAL = "residential"
    }

    override fun getSize(): Pair<JTPoint, JTPoint> {
        var maxLat = -2000.0
        var maxLon = -2000.0
        var minLat = 2000.0
        var minLon = 2000.0

        points.forEach { point ->
            if (point.latitude > maxLat) maxLat = point.latitude
            if (point.latitude < minLat) minLat = point.latitude
            if (point.longitude > maxLon) maxLon = point.longitude
            if (point.longitude < minLon) minLon = point.longitude
        }
        return Pair(JTPoint(minLat, minLon),  JTPoint(maxLat, maxLon))
    }

    override fun getType(): String = JTMap.TYPE_BUILDING
    override fun getListPoints(): MutableList<JTPoint> = points
}