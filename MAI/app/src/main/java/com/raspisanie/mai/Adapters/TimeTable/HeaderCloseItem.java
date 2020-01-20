package com.raspisanie.mai.Adapters.TimeTable;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.raspisanie.mai.R;

/**
 * ViewHolder заголовка расписания занятий.
 */
public class HeaderCloseItem extends TimeTableViewHolder {

    Button button;

    public HeaderCloseItem(@NonNull View itemView) {
        super(itemView);
        this.button = itemView.findViewById(R.id.buttonHeader);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    /**
     * Передача параметров в view элементы.
     * @param obj день недели.
     */
    @Override
    public void bind(Object obj) {

    }
}
