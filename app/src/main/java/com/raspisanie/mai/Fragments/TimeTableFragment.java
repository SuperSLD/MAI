package com.raspisanie.mai.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTableAdapter;
import com.raspisanie.mai.Classes.Week;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class TimeTableFragment extends android.app.Fragment{
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.time_table_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle(R.string.title_time_table);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.but1:
                setDaysList((int) Parametrs.getParam("thisWeek") - 1);
                break;
            case R.id.but2:
                setDaysList((int) Parametrs.getParam("thisWeek"));
                break;
            case R.id.but3:
                setDaysList((int) Parametrs.getParam("thisWeek") + 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time_table, null);
        setDaysList((int) Parametrs.getParam("thisWeek"));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().setTitle(R.string.app_name);
    }

    private void setDaysList(int week) {

        if (week < 0)
            week = 0;
        if (week >= ((Week[]) Parametrs.getParam("weeks")).length)
            week = ((Week[]) Parametrs.getParam("weeks")).length - 1;

        ListView listView = view.findViewById(R.id.listItem);
        TimeTableAdapter adapter = new TimeTableAdapter(view.getContext(),
                ((Week[]) Parametrs.getParam("weeks"))[week].getDaysList(),
                week == ((int) Parametrs.getParam("thisWeek")));
        listView.setAdapter(adapter);
    }
}
