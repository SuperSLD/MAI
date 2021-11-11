package pro.midev.juttermap.models.map_objects

import com.google.gson.JsonElement
import pro.midev.juttermap.common.JTMapObjectsFactory
import pro.midev.juttermap.extensions.toBuffer
import pro.midev.juttermap.extensions.toTriangles
import pro.midev.juttermap.geometry.JTPoint

/**
 * Модель для хранения данных
 * о карте.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTMap(json: JsonElement) {

    companion object {
        const val TYPE_BUILDING = "building"
        const val TYPE_ROAD = "road"
        const val TYPE_PLANTS = "plants"
        const val TYPE_MARKER = "marker"

        val layers = arrayOf(TYPE_PLANTS, TYPE_ROAD, TYPE_BUILDING, TYPE_MARKER)
    }

    private var mId: String? = null
    private var mName: String? = null
    private var mObjects = mutableListOf<JTMapObject>()

    private var mSize: Pair<JTPoint, JTPoint>? = null

    init {
        val obj = json.asJsonObject
        mId = obj["id"].asString
        mName = obj["name"].asString
        val objectsAndGeometry = JTMapObjectsFactory.create(obj["objects"].asJsonArray)
        mObjects = objectsAndGeometry.first
        mSize = objectsAndGeometry.second
    }

    /**
     * Получение изначальных размеров карты.
     */
    fun getSize() = mSize

    /**
     * Получение массива точек для
     * тотображения по слоям.
     */
    fun getVertexData() : FloatArray {
        val array = mutableListOf(1f, 0f, 55.81238f, 21.731160512890003f,  -1f, 0f)//mutableListOf<Float>()
        for (layer in layers) {
            val layerObjects = mObjects.filter { it.getType() == layer }
            layerObjects.forEach { obj ->
                array.addAll(obj.getListPoints().toTriangles().toBuffer())
            }
        }
        return array.toFloatArray()
    }
}