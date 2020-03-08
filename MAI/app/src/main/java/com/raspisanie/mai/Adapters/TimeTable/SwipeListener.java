package com.raspisanie.mai.Adapters.TimeTable;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwipeListener extends ItemTouchHelper.SimpleCallback {
    private static int COUNT_LISTENER = 0;
    private int listenerId;

    public SwipeListener(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        COUNT_LISTENER++;
        this.listenerId = COUNT_LISTENER;
        Logger.getLogger("mailog").log(Level.INFO, "COUNT LISTENER = " + COUNT_LISTENER);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EventCardItem) {
            Logger.getLogger("mailog").log(Level.INFO, "ON SWIPED");
            ((EventCardItem) viewHolder).deleteCard();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof EventCardItem) {
            ((EventCardItem) viewHolder).swipe(c, recyclerView, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
