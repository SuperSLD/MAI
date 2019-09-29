package com.raspisanie.mai.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raspisanie.mai.Classes.Day;
import com.raspisanie.mai.Classes.Subject;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class TimeTableSubAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Subject> objects;


    public TimeTableSubAdapter (Context context, ArrayList subj) {
        this.ctx = context;
        this.objects = subj;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_table_sub, parent, false);
        }

        ((TextView) view.findViewById(R.id.textView)).setText(objects.get(position).getName());
        ((TextView) view.findViewById(R.id.textView6)).setText(objects.get(position).getLecturer());

        ((TextView) view.findViewById(R.id.textView5)).setText(objects.get(position).getTime());
        ((TextView) view.findViewById(R.id.textView4)).setText(objects.get(position).getType());
        ((TextView) view.findViewById(R.id.textView3)).setText(objects.get(position).getPlace());

        return view;
    }
}
