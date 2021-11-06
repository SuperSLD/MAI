package pro.midev.juttermap.geometry

import pro.midev.juttermap.models.map_objects.JTMap
import kotlin.math.abs
import kotlin.math.max

/**
 * Определение стартовых значений
 * для положения камеры и зума.
 */
class JTStartGeometry (
    private val mMap: JTMap
) {
    /**
     * Вычисление стартовой позиции камеры.
     */
    fun getStartPosition() = JTPoint(
        mMap.getSize()!!.first.latitude - (mMap.getSize()!!.second.latitude - mMap.getSize()!!.first.latitude)/2,
        mMap.getSize()!!.first.longitude - (mMap.getSize()!!.second.longitude - mMap.getSize()!!.first.longitude)/2
    )

    /**
     * Получение изначального увеличения
     * карты.
     *
     * @return значение зума.
     */
    fun getStartZoom(): Float {
        val size = mMap.getSize()!!
        val s = max(
            abs(size.first.latitude - size.second.latitude),
            abs(size.first.longitude - size.second.longitude)
        )
        val v = 2
        return ((0.8f*v)/s).toFloat()
    }
}