package pro.midev.juttermap.geometry

import com.google.gson.JsonObject
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Точка карты и связанная с ней логика.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTPoint : Cloneable {
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    private val a = 6378.2
    private val b = 6356.9
    private val e = (a.pow(2) - b.pow(2))/(a.pow(2))

    constructor(lat: Double, lon: Double) {
        this.latitude = lat
        this.longitude = lon
    }

    constructor(jsonObject: JsonObject) {
        // (широта B, долгота L, высота H = 0)
        val B = jsonObject["lat"].asDouble
        val L = jsonObject["lon"].asDouble

        this.latitude  = B //(N(B) - H) * cos(B) * cos(L)
        this.longitude = L * 0.57947 //(N(B) - H) * cos(B) * sin(L)
    }

    /**
     * Поиск расстояния между
     * двумя точками.
     *
     * @param other другая точка на карте.
     * @return расстояние между текущей точкой и параметром.
     */
    fun distance(other: JTPoint): Double {
        return sqrt(
            kotlin.math.abs(other.latitude - this.latitude).pow(2) +
                    kotlin.math.abs(other.longitude - this.longitude).pow(2)
        )
    }

    /**
     * N(B) радиус кривизны первого вертикала — расстояние
     * по нормали к эллипсоиду от точки пересечения
     * поверхности эллипсоида нормалью до оси oZ
     */
    private fun N(B: Double): Double {
        return a/(sqrt(1 - e.pow(2)* sin(B).pow(2)))
    }

    public override fun clone(): Any {
        return JTPoint(this.latitude, this.longitude)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is JTPoint) this.latitude == other.latitude && this.longitude == other.longitude else false
    }
}