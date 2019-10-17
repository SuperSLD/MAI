package com.raspisanie.mai.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.raspisanie.mai.Classes.Day;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Adapters.TimeTableAdapter;
import com.raspisanie.mai.Classes.Week;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableFragment extends android.app.Fragment{
    View view;
    private ArrayList<Day> day = new ArrayList<>();

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Текущая неделя");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.but1:
                setDaysList((int) Parametrs.getParam("thisWeek") - 1);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Предыдущая неделя");
                break;
            case R.id.but2:
                setDaysList((int) Parametrs.getParam("thisWeek"));
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Текущая неделя");
                break;
            case R.id.but3:
                setDaysList((int) Parametrs.getParam("thisWeek") + 1);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Следующая неделя");
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
        day.clear();

        if (week < 0)
            week = 0;
        if (week >= ((Week[]) Parametrs.getParam("weeks")).length)
            week = ((Week[]) Parametrs.getParam("weeks")).length - 1;

        for (int i = 0;
             i < ((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().size(); i++) {
            Calendar calendar = Calendar.getInstance();
            if (week == ((int) Parametrs.getParam("thisWeek"))) {
                if (Integer.parseInt(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i).getDate().substring(0, 2)) >=
                        calendar.get(Calendar.DAY_OF_MONTH)) {
                    day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
                }
            } else {
                day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
            }
        }

        ListView listView = view.findViewById(R.id.listItem);
        TimeTableAdapter adapter = new TimeTableAdapter(view.getContext(),
                day, week == ((int) Parametrs.getParam("thisWeek")));
        listView.setAdapter(adapter);
    }
}
