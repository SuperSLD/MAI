package pro.midev.juttermap.extensions

import pro.midev.juttermap.geometry.JTPoint
import pro.midev.juttermap.geometry.JTTriangle
import kotlin.math.abs

/**
 * Получение элемента из списка
 * без учета границ.
 */
fun <T> List<T>.getLooped(i: Int) =
    this[if (i >= this.size) i % this.size else if (i >= 0) i else this.size - abs(i)]

/**
 * Получение из списка точек, набора
 * треугольников.
 *
 * @return саписок треугольников.
 */
fun List<JTPoint>.toTriangles() : List<JTTriangle> {
    val triangles = mutableListOf<JTTriangle>()
    val points = mutableListOf<JTPoint>()
    forEach { point ->
        if (points.size < 15) points.add(point.clone() as JTPoint)
    }

    var maxIndex = 0
    var max = Double.MIN_VALUE
    for (i in points.indices) {
        if (points[i].latitude > max) {
            max = points[i].latitude
            maxIndex = i
        }
    }
    /*
           |i  j  k |
     VxU = |x1 y1 0 | = i(y1*0-y2*0) - j(x1*0 - x2*0) + k(x1*y2- x2*y1) = x1*y2 - x2*y1
           |x2 y2 0 |
    */
    // определяем изначальное направление обхода полигона.
    val vectorsOrientation = vectorMultiplicationAbs(
        getLooped(maxIndex - 1),
        getLooped(maxIndex),
        getLooped(maxIndex + 1)
    )

    var i = 0
    while (points.size > 3) {
        if (vectorMultiplicationAbs(
                points.getLooped(i),
                points.getLooped(i + 1),
                points.getLooped(i + 2)
            ) == vectorsOrientation
        ) {
            var correct = true
            for (j in points.indices) {
                //(x.get(j) != x.get(getI(i)) && x.get(j) != x.get(getI(i+1)) && x.get(j) != x.get(getI(i+2))) &&
                //(y.get(j) != y.get(getI(i)) && y.get(j) != y.get(getI(i+1)) && y.get(j) != y.get(getI(i+2))) &&
                if (points[j] != points.getLooped(i) && points[j] != points.getLooped(i + 1) && points[j] != points.getLooped(i + 2) &&
                    JTTriangle.pointIn(
                        points.getLooped(i),
                        points.getLooped(i + 1),
                        points.getLooped(i + 2),
                        points[j]
                    )
                ) {
                    correct = false
                }
            }
            if (correct) {
                triangles.add(
                    JTTriangle(
                        points.getLooped(i),
                        points.getLooped(i + 1),
                        points.getLooped(i + 2)
                    )
                )
                points.remove(points.getLooped(i + 1))
            }
        }
        i++
    }
    triangles.add(
        JTTriangle(
            points[0], points[1], points[2]
        )
    )

    return triangles
}

/**
 * Определение направления вектора полученного в результате
 * векторного умножения двух сторон полигона.
 *
 * @param p1 точка
 * @param p2 центральная точка
 * @param p3 точка
 *
 * @return совпажает ли направление вектора с положительным направлением.
 */
private fun vectorMultiplicationAbs(p1: JTPoint, p2: JTPoint, p3: JTPoint): Boolean {
    val x1: Double = p1.latitude - p2.latitude
    val y1: Double = p1.longitude - p2.longitude
    val x2: Double = p2.latitude - p3.latitude
    val y2: Double = p2.longitude - p3.longitude
    return x1 * y2 - x2 * y1 >= 0
}

/**
 * Преобразование списка треугольников в массив точек
 * для отображения.
 */
fun List<JTTriangle>.toBuffer(): MutableList<Float> {
    val vertex = mutableListOf<Double>()
    forEach { triangle ->
        for (j in triangle.getPoints().indices) {
            vertex.add(triangle.getPoints()[j]!!.latitude)
            vertex.add(triangle.getPoints()[j]!!.longitude)
        }
    }
    return vertex.map { it.toFloat() }.toMutableList()
}