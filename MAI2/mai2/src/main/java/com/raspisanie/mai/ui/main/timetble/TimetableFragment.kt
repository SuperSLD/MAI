package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.ui.view.ToolbarBigView
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment

/**
 * Страшный фрагмент, со сложной логикой.
 * И дальше все будет только хуже.
 */
class TimetableFragment : BaseFragment(R.layout.fragment_timetable), TimetableView {

    @InjectPresenter
    lateinit var presenter: TimetablePresenter

    private val adapter by lazy { TimetableAdapter(
            presenter::onDaysListItemClick
    ) }
    private val headerAdapter by lazy { TimetableHeaderAdapter(
            presenter::onDayHeaderClick
    ) }

    private val scrollDaysListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

            if (firstVisibleItemPosition == 0) {
                headerAdapter.selectItem((adapter.getItemByPosition(0) as DayLocal).date)
                scrollToDay((adapter.getItemByPosition(0) as DayLocal).date)
            } else if (firstVisibleItemPosition > 0) {
                if (dy > 0) {
                    val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val lastVisibleItem = adapter.getItemByPosition(lastVisibleItemPosition)
                    if (lastVisibleItem is DayLocal) {
                        headerAdapter.selectItem(lastVisibleItem.date)
                        scrollToDay(lastVisibleItem.date)
                    }
                } else {
                    val firstVisibleItem = adapter.getItemByPosition(firstVisibleItemPosition)
                    if (firstVisibleItem is DayLocal) {
                        headerAdapter.selectItem(firstVisibleItem.date)
                        scrollToDay(firstVisibleItem.date)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        vToolbar.init(
                title = R.string.timetable_title_current,
                action = ToolbarBigView.ToolbarAction(
                        stringId = R.string.timetable_dialog_other_week_button,
                        iconId = R.drawable.ic_calendar_small,
                        action = { presenter.selectWeekDialog() }
                )
        )

        with(rvWeek) {
            adapter = this@TimetableFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(scrollDaysListener)
        }
        with(rvWeekHeader) {
            adapter = this@TimetableFragment.headerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    override fun selectDay(date: String) {
        headerAdapter.selectItem(date)
        rvWeek.removeOnScrollListener(scrollDaysListener)
        scrollToDay(date)
        scrollToDayInWeek(date)
    }

    override fun shoWeek(week: WeekLocal?) {
        if (week == null) {

        } else {
            adapter.addData(week)
            headerAdapter.addData(week)
        }
    }

    override fun showErrorLoading() {
        vgMain.visibility = View.GONE
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        vToolbar.disableAction()
        cvError.setOnClickListener {
            presenter.loadSchedule()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgMain.visibility = if (show) View.GONE else View.VISIBLE
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        if (show) {
            vToolbar.disableAction()
        } else {
            vToolbar.enableAction()
        }
    }

    private fun scrollToDayInWeek(date: String) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return 100
            }

            override fun onStop() {
                super.onStop()
                Handler().postDelayed({ rvWeek.addOnScrollListener(scrollDaysListener) }, 400)
            }
        }
        smoothScroller.targetPosition = adapter.getItemPosition(date)
        (rvWeek.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
    }

    private fun scrollToDay(date: String) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return 150
            }
        }
        smoothScroller.targetPosition = headerAdapter.getItemPosition(date)
        (rvWeekHeader.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
    }

    override fun onBackPressed() {
        presenter.back()
    }
}