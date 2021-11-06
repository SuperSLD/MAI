package pro.midev.juttermap.geometry

/**
 * Треугольник.
 * @author Леонид Соляной (solyanoy.leonid@gmail.com)
 */
class JTTriangle {

    private var a: JTPoint? = null
    private var b: JTPoint? = null
    private var c: JTPoint? = null

    /**
     * Создание экземпляра треугольника
     * из трех независимых координат.
     */
    constructor(
        x1: Double, y1: Double,
        x2: Double, y2: Double,
        x3: Double, y3: Double
    ) {
        this.a = JTPoint(x1, y1)
        this.b = JTPoint(x2, y2)
        this.c = JTPoint(x3, y3)
    }

    /**
     * Создание экземпляра треугольника
     * из трех объектов [JTPoint]
     */
    constructor(
        a: JTPoint, b: JTPoint, c: JTPoint
    ) {
        this.a = a
        this.b = b
        this.c = c
    }

    /**
     * Получение всех точек
     * массивом.
     */
    fun getPoints() = arrayOf(a, b, c)

    /**
     * Проверка на принадлежность точки к треугольнику.
     * @return true если тока в треугольнике.
     */
    fun pointIn(point: JTPoint): Boolean {
        val p1 = (a!!.latitude - point.latitude) * (b!!.longitude - a!!.longitude) - (b!!.latitude - a!!.latitude) * (a!!.longitude - point.longitude)
        val p2 = (b!!.latitude - point.latitude) * (c!!.longitude - b!!.longitude) - (c!!.latitude - b!!.latitude) * (b!!.longitude - point.longitude)
        val p3 = (c!!.latitude - point.longitude) * (a!!.longitude - c!!.longitude) - (a!!.latitude - c!!.longitude) * (c!!.longitude - point.longitude)
        return p1 > 0 && p2 > 0 && p3 > 0 || p1 < 0 && p2 < 0 && p3 < 0
    }

    override fun toString() =
        "[(" + a?.latitude + ";" + a?.longitude + "),(" + b?.latitude + ";" + b?.longitude + "),(" + c?.latitude + ";" + c?.longitude + ")]"

    companion object {
        fun pointIn(p1: JTPoint, p2: JTPoint, p3: JTPoint, p0: JTPoint) : Boolean {
            val triangle = JTTriangle(p1, p2, p3)
            return triangle.pointIn(p0)
        }
    }
}