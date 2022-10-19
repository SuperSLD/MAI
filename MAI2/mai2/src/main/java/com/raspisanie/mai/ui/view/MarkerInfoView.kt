package com.raspisanie.mai.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_marker_info.view.*
import online.jutter.roadmapview.RMColorData
import online.jutter.roadmapview.data.models.map.RMMarker

class MarkerInfoView  : RelativeLayout {

    private var mSymbol = "A"
    private var mColor = R.drawable.shape_orange_eclipse_48
    private var mColorData = RMColorData()
    private var mTitleRes = 0
    private var mDescriptionRes = 0
    private var mMarker: RMMarker? = null
    private var mMapEnabled = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    init {
        View.inflate(context, R.layout.view_marker_info, this)
    }

    fun setColor(color: String, cd: RMColorData) {
        val symbolBack = ContextCompat.getDrawable(context, R.drawable.shape_orange_eclipse_48)
        mColorData = cd
        DrawableCompat.setTint(symbolBack!!, Color.parseColor(color))
        tvSymbol.background = symbolBack
    }

    fun setSymbol(s: String) {
        tvSymbol.text = s
    }

    fun setTitle(s: String) {
        tvTitle.text = s
    }

    fun setDescription(s: String) {
        tvDescription.text = s
    }

    fun disableMap() {
        mMapEnabled = false
    }

    fun addMarker(marker: RMMarker?) {
        if (marker != null && mMapEnabled) {
            mapView.setColorData(mColorData)
            mapView.onLoad {
                mapView.setCameraPosition(marker.getPoint(), 0.0020342574F)
                mapView.addMarkers(listOf(marker))
            }
            mapView.canScroll(false)
            mapView.init()
        }
        tvSelected.visibility = if (marker == null) GONE else VISIBLE
        mapView.visibility = if (marker == null || !mMapEnabled) GONE else VISIBLE
        mMarker = marker
    }

    fun marker() = mMarker

    fun onClick(block: () -> Unit) {
        mainCard.setOnClickListener {
            block()
        }
    }
}