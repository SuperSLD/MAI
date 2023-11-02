package com.raspisanie.mai.ui.main.info.roadmap

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.raspisanie.mai.common.extesions.getIsDayTheme
import online.jutter.roadmapview.RMColorData
import online.jutter.roadmapview.extensions.createColor
import online.jutter.roadmapview.extensions.createColorInt

fun Context.getMapColorData(
    back: FloatArray = createColor(255, 255, 255),
    backD: FloatArray = colorToFloatArray("#121212"),
): RMColorData {
    val isDay = getIsDayTheme()
    return if (isDay) {
        RMColorData(
            buildingColor = colorToFloatArray("#F1F1F1"),
            buildingWithFloorsColor = colorToFloatArray("#8ECAFC"),
            backColor = back,
            navBorder = createColorInt(0, 0, 0),
            roadColors = listOf(
                colorToFloatArray("#DCDCDC"), // серый
                colorToFloatArray("#DCDCDC"), // серый
                colorToFloatArray("#DCDCDC"), // серый
                colorToFloatArray("#DCDCDC"), // серый
            ),
            floorBackColor = createColor(103, 103, 103),
        )
    } else {
        RMColorData(
            grassColor = colorToFloatArray("#1C3A2F"),
            buildingColor = colorToFloatArray("#303030"),
            buildingWithFloorsColor = colorToFloatArray("#0D83E4"),
            backColor = backD,
            navBorder = createColorInt(255, 255, 255),
            roadColors = listOf(
                colorToFloatArray("#505050"), // серый
                colorToFloatArray("#505050"), // серый
                colorToFloatArray("#505050"), // серый
                colorToFloatArray("#505050"), // серый
            ),
            floorBackColor = createColor(10, 10, 10),
        )
    }
}

fun Fragment.getMapColorData(
    back: FloatArray = createColor(255, 255, 255),
    backD: FloatArray = createColor(12, 12, 12),
) = requireContext().getMapColorData(back, backD)

fun colorToFloatArray(string: String): FloatArray {
    val c = Color.parseColor(string)
    Color.alpha(c)
    return floatArrayOf(
        Color.red(c)/255F,
        Color.green(c)/255F,
        Color.blue(c)/255F,
        Color.alpha(c)/255F,
    )
}