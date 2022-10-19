package com.raspisanie.mai.ui.main.info.roadmap.navigation
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.main.info.roadmap.getMapColorData
import kotlinx.android.synthetic.main.fragment_navigation.*
import online.jutter.roadmapview.data.models.map.RMMarker
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopAndBottomPadding

class NavigationFragment : BaseFragment(R.layout.fragment_navigation), NavigationView {

    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navContainer.addSystemTopAndBottomPadding()
        icBack.setOnClickListener { onBackPressed() }
        updateButtonState()
        btnDone.setOnClickListener {
            presenter.onDone(vMarkerStart.marker()!!, vMarkerEnd.marker()!!)
        }

        initMarkerInfo()
    }

    private fun initMarkerInfo() {
        with(vMarkerStart) {
            setTitle(getString(R.string.nav_a_title))
            setDescription(getString(R.string.nav_a_desk))
            setSymbol("A")
            setColor("#F2514B", getMapColorData(
                back = Color.rgb(248, 248, 248)
            ))
            onClick {
                presenter.onSelectStart()
            }
        }
        with(vMarkerEnd) {
            setTitle(getString(R.string.nav_b_title))
            setDescription(getString(R.string.nav_b_desk))
            setSymbol("Б")
            setColor("#636363", getMapColorData(
                back = Color.rgb(248, 248, 248)
            ))
            onClick {
                presenter.onSelectEnd()
            }
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun addStartMarker(marker: RMMarker) {
        vMarkerStart.addMarker(marker)
        updateButtonState()
    }

    override fun addEndMarker(marker: RMMarker) {
        vMarkerEnd.addMarker(marker)
        updateButtonState()
    }

    private fun updateButtonState() {
        btnDone.isEnabled = vMarkerEnd.marker() != null && vMarkerStart.marker() != null
    }
}