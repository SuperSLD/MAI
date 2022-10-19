package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.controllers.SelectWeekController
import com.raspisanie.mai.common.extesions.openWebLink
import com.raspisanie.mai.domain.models.DayLocal
import com.raspisanie.mai.domain.models.WeekLocal
import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.ui.view.ToolbarBigView
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.fragment_timetable.rvWeek
import kotlinx.android.synthetic.main.fragment_timetable.vToolbar
import kotlinx.android.synthetic.main.fragment_timetable.vgContent
import kotlinx.android.synthetic.main.fragment_timetable.vgEmpty
import kotlinx.android.synthetic.main.fragment_timetable.vgMain
import kotlinx.android.synthetic.main.fragment_timetable.vgSelectGroup
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopPadding

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

        coordinator.addSystemTopPadding()

        vToolbar.init(
                title = R.string.timetable_title_current,
                action = ToolbarBigView.ToolbarAction(
                        stringId = R.string.timetable_dialog_other_week_button,
                        iconId = R.drawable.ic_calendar_small,
                        action = { presenter.selectWeekDialog() }
                ),
                padding = false
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

        btnSelectGroup.setOnClickListener {
            presenter.goToSettings()
        }
    }

    override fun selectDay(date: String) {
        headerAdapter.selectItem(date)
        rvWeek.removeOnScrollListener(scrollDaysListener)
        scrollToDay(date)
        scrollToDayInWeek(date)
    }

    override fun shoWeek(week: WeekLocal?, number: Int) {
        if (week == null) {
            showEmpty(true)
        } else {
            showEmpty(false)
            adapter.addData(week)
            headerAdapter.addData(week)

            rvWeek.layoutManager?.scrollToPosition(0)
            rvWeekHeader.layoutManager?.scrollToPosition(0)
            if (number == SelectWeekController.THIS_WEEK) {
                val currentDay = week.getCurrentDay()
                if (currentDay == null) {
                    rvWeek.layoutManager?.scrollToPosition(adapter.itemsSize() - 1)
                    rvWeekHeader.layoutManager?.scrollToPosition(week.days.size - 1)
                    headerAdapter.selectPosition(week.days.size - 1)
                } else {
                    rvWeek.layoutManager?.scrollToPosition(adapter.getItemPosition(currentDay.date))
                    rvWeekHeader.layoutManager?.scrollToPosition(headerAdapter.getItemPosition(currentDay.date))
                }
            }
            enableAppbar()
        }
    }

    private fun showEmpty(show: Boolean) {
        vgEmpty.visibility = if (show) View.VISIBLE else View.GONE
        vgContent.visibility = if (show) View.GONE else View.VISIBLE
        openAndDisableAppbar()
    }

    override fun showTitle(week: Int) {
        when(week) {
            SelectWeekController.THIS_WEEK -> vToolbar.setTitle(R.string.timetable_dialog_this_week_button)
            SelectWeekController.NEXT_WEEK -> vToolbar.setTitle(R.string.timetable_dialog_next_week_button)
            SelectWeekController.PREVIOUS_WEEK -> vToolbar.setTitle(R.string.timetable_dialog_previous_week)
            else -> vToolbar.setTitle(getString(R.string.timetable_week_other, week))
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
        openAndDisableAppbar()
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

    override fun showEmptyGroups() {
        vgEmpty.visibility = View.GONE
        vgContent.visibility = View.GONE
        vgSelectGroup.visibility = View.VISIBLE
        openAndDisableAppbar()
    }

    override fun openOnlineLink(link: String) {
        requireContext().openWebLink(link)
    }

    override fun showGroup(group: GroupRealm?) {
        if (group == null) {
            tvGroupTitle.visibility = View.GONE
            tvGroupTitle.visibility = View.GONE
        } else {
            tvGroupTitle.visibility = View.VISIBLE
            tvGroupTitle.visibility = View.VISIBLE
            tvGroup.text = group.name
        }
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

    /**
     * Открываем аппбар и не даем
     * его закрыть, если нет контента для
     * скрола.
     * Ш
     */
    private fun openAndDisableAppbar() {
        appbar.setExpanded(true)
        val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
    }

    /**
     * Включение аппабра.
     * (разрешаем его скролить)
     */
    private fun enableAppbar() {
        val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return true
            }
        })
    }

    override fun onBackPressed() {
        presenter.back()
    }
}