package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.R;

/**
 * Элемент для перехода к следующей неделе.
 */
public class NextWeekItem extends TimeTableViewHolder {
    private MainActivity activity;

    private Button nextButton;

    public NextWeekItem(@NonNull View itemView, MainActivity activity) {
        super(itemView);
        this.activity = activity;

        this.nextButton = itemView.findViewById(R.id.buttonNext);
    }

    @Override
    public void bind(Object obj) {
        nextButton.setOnClickListener(v -> {
            activity.setNextWeek();
        });
    }
}
