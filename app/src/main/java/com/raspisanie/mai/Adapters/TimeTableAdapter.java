package com.raspisanie.mai.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Classes.Day;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Day> objects;
    private boolean now;

    public TimeTableAdapter(Context context, ArrayList days, boolean now) {
        this.now = now;
        this.ctx = context;
        this.objects = days;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view =  lInflater.inflate(R.layout.item_table_main, parent, false);

        Calendar calendar = Calendar.getInstance();
        if (now) {
            if (Integer.parseInt(objects.get(position).getDate().substring(0, 2)) !=
                calendar.get(Calendar.DAY_OF_MONTH)) {
                view.findViewById(R.id.dayHeader).setBackgroundResource(R.color.dayGreyHeader);
            }
        } else {
            view.findViewById(R.id.dayHeader).setBackgroundResource(R.color.dayGreyHeader);
        }

        System.out.println("getView");
        ((TextView) view.findViewById(R.id.textView1)).setText(objects.get(position).getDate());
        ((TextView) view.findViewById(R.id.textView2)).setText(objects.get(position).getName());

        TimeTableSubAdapter adapter = new TimeTableSubAdapter(ctx, objects.get(position).getSubjectList());

        LinearLayout newLinearLayout = new LinearLayout(ctx);
        newLinearLayout.setOrientation(LinearLayout.VERTICAL);

        newLinearLayout.removeAllViews();
        for(int i = 0 ; i < objects.get(position).getSubjectList().size(); i++)
            newLinearLayout.addView(adapter.getView(i, null, newLinearLayout));

        ((LinearLayout) view.findViewById(R.id.listItem)).removeAllViews();
        ((LinearLayout) view.findViewById(R.id.listItem)).addView(newLinearLayout);

        return view;
    }
}
