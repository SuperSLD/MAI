package com.raspisanie.mai.Adapters.TimeTable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Шаблон для ViewHolder расписания предметов.
 */
public abstract class TimeTableViewHolder extends RecyclerView.ViewHolder {

    public TimeTableViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Передача параметров в View элементы.
     * @param obj объект входных параметров.
     */
    public abstract void bind(Object obj);
}
