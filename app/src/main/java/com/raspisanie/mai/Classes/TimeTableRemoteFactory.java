package com.raspisanie.mai.Classes;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raspisanie.mai.R;

import java.util.ArrayList;

public class TimeTableRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Subject> data;
    Context context;
    int widgetID;

    public TimeTableRemoteFactory(Context ctx, ArrayList<Subject> data) {
        this.context = ctx;
        this.data = data;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.item_table_sub);
        rView.setTextViewText(R.id.textView, data.get(i).getName());
        rView.setTextViewText(R.id.textView6, data.get(i).getLecturer());

        rView.setTextViewText(R.id.textView5, data.get(i).getName());
        rView.setTextViewText(R.id.textView4, data.get(i).getLecturer());
        rView.setTextViewText(R.id.textView3, data.get(i).getName());
        return rView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
