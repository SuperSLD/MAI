package com.raspisanie.mai.ui.main.info.roadmap

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.raspisanie.mai.common.extesions.getIsDayTheme
import online.jutter.roadmapview.RMColorData
import online.jutter.roadmapview.extensions.createColor

fun Context.getMapColorData(
    back: Int = createColor(255, 255, 255),
    backD: Int = Color.parseColor("#121212"),
): RMColorData {
    val isDay = getIsDayTheme()
    return if (isDay) {
        RMColorData(
            buildingColor = Color.parseColor("#F1F1F1"),
            buildingWithFloorsColor = Color.parseColor("#8ECAFC"),
            backColor = back,
            navBorder = createColor(0, 0, 0),
            roadColors = listOf(
                Color.parseColor("#DCDCDC"), // серый
                Color.parseColor("#DCDCDC"), // серый
                Color.parseColor("#DCDCDC"), // серый
                Color.parseColor("#DCDCDC"), // серый
            ),
            floorBackColor = createColor(103, 103, 103),
        )
    } else {
        RMColorData(
            buildingColor = Color.parseColor("#303030"),
            buildingWithFloorsColor = Color.parseColor("#0D83E4"),
            backColor = backD,
            navBorder = createColor(255, 255, 255),
            roadColors = listOf(
                Color.parseColor("#505050"), // серый
                Color.parseColor("#505050"), // серый
                Color.parseColor("#505050"), // серый
                Color.parseColor("#505050"), // серый
            ),
            floorBackColor = createColor(10, 10, 10),
        )
    }
}

fun Fragment.getMapColorData(
    back: Int = createColor(255, 255, 255),
    backD: Int = createColor(12, 12, 12),
) = requireContext().getMapColorData(back, backD)