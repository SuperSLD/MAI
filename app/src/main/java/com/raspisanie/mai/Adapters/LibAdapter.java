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

public class LibAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<SimpleTree<String>> objects;

    public LibAdapter(Context context, ArrayList korpus) {
        this.ctx = context;
        this.objects = korpus;
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
            view = lInflater.inflate(R.layout.item_sport_group, parent, false);
        }

        ((TextView) view.findViewById(R.id.tableText)).setText(objects.get(i).getValue());

        LibSubAdapter adapter = new LibSubAdapter(ctx, objects.get(i).getChildList());

        LinearLayout newLinearLayout = new LinearLayout(ctx);
        newLinearLayout.setOrientation(LinearLayout.VERTICAL);

        newLinearLayout.removeAllViews();
        for (int j = 0 ; j < objects.get(i).getChildList().size(); j++)
            newLinearLayout.addView(adapter.getView(j, null, newLinearLayout));

        ((LinearLayout) view.findViewById(R.id.tableList)).removeAllViews();
        ((LinearLayout) view.findViewById(R.id.tableList)).addView(newLinearLayout);

        return view;
    }
}
