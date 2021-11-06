package pro.midev.juttermap.common

import android.graphics.Color
import pro.midev.juttermap.extensions.createColor

/**
 * Информация о внешнем виде объектов
 * на карте.
 */
data class JTColorData(
    val buildingColors: Array<Int> = arrayOf(
        createColor(142, 202, 252),
        createColor(241, 241, 241),
        createColor(213, 219, 221)
    ),
    val roadColors: Array<Int> = arrayOf(
        createColor(220, 220, 220)
    ),
    val backColor: Int = createColor(255, 255, 255)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JTColorData

        if (!buildingColors.contentDeepEquals(other.buildingColors)) return false
        if (!roadColors.contentDeepEquals(other.roadColors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buildingColors.contentDeepHashCode()
        result = 31 * result + roadColors.contentDeepHashCode()
        return result
    }

}