package com.raspisanie.mai.ui.main.info.campus_map

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getIsDayTheme
import kotlinx.android.synthetic.main.fragment_campus_map.*
import pro.midev.juttermap.common.JTColorData
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemTopPadding

class CampusMapFragment : BaseFragment(R.layout.fragment_campus_map), MvpView {

    @InjectPresenter
    lateinit var presenter: CampusMapPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvBack.setOnClickListener { onBackPressed() }
        vgButtonContainer.addSystemTopPadding()

        initMapView()
    }

    private fun initMapView() {
        mapView.init()
        mapView.setColorData(createColorData())
    }

    /**
     * Выбор цвета карты в зависимости от текущей
     * темы приложения.
     */
    private fun createColorData() = if (requireContext().getIsDayTheme()) {
        JTColorData(
            buildingColors = arrayOf(
                Color.rgb(142, 202, 252),
                Color.rgb(241, 241, 241),
                Color.rgb(213, 219, 221)
            ),
            roadColors = arrayOf(
                Color.rgb(220, 220, 220)
            ),
            backColor = Color.rgb(255, 255, 255)
        )
    } else {
        JTColorData(
            buildingColors = arrayOf(
                Color.rgb(13, 131, 228),
                Color.rgb(48, 48, 48),
                Color.rgb(37, 37, 37)
            ),
            roadColors = arrayOf(
                Color.rgb(80, 80, 80)
            ),
            backColor = Color.rgb(18, 18, 18)
        )
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onBackPressed() {
        presenter.back()
    }
}