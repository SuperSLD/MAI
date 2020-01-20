package com.raspisanie.mai.Adapters.TimeTable;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.R;

import java.util.Calendar;

/**
 * ViewHolder дня расписания занятий.
 */
public class TimeTableItem extends TimeTableViewHolder {
    private View view;
    private boolean now;

    private View dayHeader;
    private TextView textDayName;
    private TextView textDayDate;
    private LinearLayout linearLayout;

    public TimeTableItem(@NonNull View itemView, boolean now) {
        super(itemView);
        this.view = itemView;
        this.dayHeader    = itemView.findViewById(R.id.dayHeader);
        this.textDayName  = itemView.findViewById(R.id.textView2);
        this.textDayDate  = itemView.findViewById(R.id.textView1);
        this.linearLayout = itemView.findViewById(R.id.listItem);
        this.now = now;
    }

    /**
     * Передача параметров в view элементы.
     * @param object день недели.
     */
    @Override
    public void bind(Object object) {
        Day day = (Day) object;
        Calendar calendar = Calendar.getInstance();
        if (now) {
            if (Integer.parseInt(day.getDate().substring(0, 2)) !=
                    calendar.get(Calendar.DAY_OF_MONTH)) {
                dayHeader.setBackgroundResource(R.color.dayGreyHeader);
            }
        } else {
            dayHeader.setBackgroundResource(R.color.dayGreyHeader);
        }


        textDayDate.setText(day.getDate());
        textDayName.setText(day.getName());

        TimeTableSubAdapter adapter = new TimeTableSubAdapter(view.getContext(), day.getSubjectList());

        LinearLayout newLinearLayout = new LinearLayout(view.getContext());
        newLinearLayout.setOrientation(LinearLayout.VERTICAL);

        newLinearLayout.removeAllViews();
        for(int i = 0 ; i < day.getSubjectList().size(); i++)
            newLinearLayout.addView(adapter.getView(i, null, newLinearLayout));

        linearLayout.removeAllViews();
        linearLayout.addView(newLinearLayout);
    }
}
