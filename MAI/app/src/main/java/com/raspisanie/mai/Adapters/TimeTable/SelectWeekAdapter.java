package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

public class SelectWeekAdapter extends BaseAdapter {
    private Week[] weeks;
    private LayoutInflater lInflater;
    private Context context;

    public SelectWeekAdapter(Context context, Week[] weeks) {
        this.weeks = weeks;
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
    public View getView(int i, View view, ViewGroup convertView) {
        view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_select_week_list, convertView, false);
        }



        return view;
    }
}
