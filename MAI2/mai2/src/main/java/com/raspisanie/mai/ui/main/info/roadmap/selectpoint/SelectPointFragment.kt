package com.raspisanie.mai.ui.main.info.roadmap.selectpoint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.roadmap.getMapColorData
import kotlinx.android.synthetic.main.fragment_selectpoint.*
import kotlinx.android.synthetic.main.fragment_selectpoint.mapView
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopAndBottomPadding

class SelectPointFragment : BaseFragment(R.layout.fragment_selectpoint), MvpView {

    companion object {
        const val ID = "id"
        const val TITLE_RES = "title_res"
        const val DESCRIPTION_RES = "description_res"
        const val SYMBOL = "symbol"
        const val COLOR = "color"

        fun createInstance(
            id: String,
            titleRes: Int,
            descriptionRes: Int,
            symbol: String,
            color: String,
        ) = SelectPointFragment().apply {
            arguments = Bundle()
            arguments?.putString(ID, id)
            arguments?.putInt(TITLE_RES, titleRes)
            arguments?.putInt(DESCRIPTION_RES, descriptionRes)
            arguments?.putString(SYMBOL, symbol)
            arguments?.putString(COLOR, color)
        }
    }

    @InjectPresenter
    lateinit var presenter: SelectPointPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuContainer.addSystemTopAndBottomPadding()
        icBack.setOnClickListener { onBackPressed() }

        initMapView()
        initMarkerData()
    }

    private fun initMapView() {
        with(mapView) {
            setColorData(getMapColorData())
            setMaxZoom(0.04773629F)
            hideFloors()
            init()
        }
    }

    private fun initMarkerData() {
        tvTitle.setText(arguments?.getInt(TITLE_RES)!!)
        tvDescription.setText(arguments?.getInt(DESCRIPTION_RES)!!)
        tvSymbol.text = arguments?.getString(SYMBOL)

        val symbolBack = ContextCompat.getDrawable(requireContext(), R.drawable.shape_orange_eclipse_48)
        DrawableCompat.setTint(symbolBack!!, Color.parseColor(arguments?.getString(COLOR)!!))
        tvSymbol.background = symbolBack

        with(markerView) {
            setSymbol(arguments?.getString(SYMBOL)!!)
            setColor(arguments?.getString(COLOR)!!)
            setTextColor("#FFFFFF")
            connect(mapView)
        }

        btnDone.setOnClickListener {
            presenter.onDoneClick(markerView.getMarker(arguments?.getString(ID)!!))
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}