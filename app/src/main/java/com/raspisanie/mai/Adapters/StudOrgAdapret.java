package com.raspisanie.mai.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class StudOrgAdapret extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<SimpleTree<String>> objects;

    public StudOrgAdapret(Context context, ArrayList list) {
        this.ctx = context;
        this.objects = list;
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
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_stud_org, parent, false);
        }

        System.out.println(i);
        String[] text = objects.get(i).getValue().split("<!>");
        ((TextView) view.findViewById(R.id.t0)).setText(text[0]);
        ((TextView) view.findViewById(R.id.t1)).setText(text[1]);
        ((TextView) view.findViewById(R.id.t2)).setText(text[2]);
        ((TextView) view.findViewById(R.id.t3)).setText(text[3]);

        return view;
    }
}
