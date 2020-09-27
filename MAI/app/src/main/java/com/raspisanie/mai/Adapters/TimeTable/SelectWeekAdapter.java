package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.TimeTableManager;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.Fragments.SelectWeekDialogFragment;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class SelectWeekAdapter extends BaseAdapter {
    private ArrayList<Week> weeks;
    private LayoutInflater lInflater;
    private Context context;
    private SelectWeekDialogFragment dialog;

    public SelectWeekAdapter(Context context, ArrayList<Week> weeks, SelectWeekDialogFragment dialog) {
        this.weeks = weeks;
        this.dialog = dialog;
        this.context = context;
        this.lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return weeks.size();
    }

    @Override
    public Object getItem(int i) {
        return weeks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_select_week_list, viewGroup, false);
        }

        TextView number = view.findViewById(R.id.number);
        TextView date = view.findViewById(R.id.date);
        TextView current = view.findViewById(R.id.current);

        number.setText("Неделя " + weeks.get(i).getN());
        date.setText(weeks.get(i).getDate());

        Button button = view.findViewById(R.id.closeButton);
        if (weeks.get(i).getN() == TimeTableManager.getInstance().getThisWeek() + 1) {
            button.setVisibility(View.GONE);
            current.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);
            current.setVisibility(View.GONE);
        }

        button.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.setPosition(i);
            dialog.runButtonAction();
        });

        return view;
    }
}
