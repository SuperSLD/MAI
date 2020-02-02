package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.Fragments.SelectWeekDialogFragment;
import com.raspisanie.mai.R;

public class SelectWeekAdapter extends BaseAdapter {
    private Week[] weeks;
    private LayoutInflater lInflater;
    private Context context;
    private SelectWeekDialogFragment dialog;

    public SelectWeekAdapter(Context context, Week[] weeks, SelectWeekDialogFragment dialog) {
        this.weeks = weeks;
        this.dialog = dialog;
        this.context = context;
        this.lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return weeks.length;
    }

    @Override
    public Object getItem(int i) {
        return weeks[i];
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

        ((TextView) view.findViewById(R.id.number)).setText("Неделя " + weeks[i].getN());
        ((TextView) view.findViewById(R.id.date)).setText(weeks[i].getDate());

        Button button = view.findViewById(R.id.closeButton);

        button.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.setPosition(i);
            dialog.runButtonAction();
        });

        return view;
    }
}
