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
    private final ArrayList<Day> day = new ArrayList<>();
    private boolean notFirstDay = false;

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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_time_table, null);
            setDaysList((int) Parametrs.getParam("thisWeek"));

            ListView listView = view.findViewById(R.id.listItem);
            if (notFirstDay) {
                final View header = inflater.inflate(R.layout.header_time_table, null);
                listView.addHeaderView(header);

                header.findViewById(R.id.buttonHeader).setOnClickListener(v -> {
                            listView.removeHeaderView(header);
                            int thisWeek = (int) Parametrs.getParam("thisWeek");
                            //for (int i = ; i )
                        }
                );
            }
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Передаем в ListView adapter с списком дней текущей недели.
     * Сам список дней объявлен глобальным чтоб его можно было динамически изменять.
     * @param week номер текущей недели.
     */
    private void setDaysList(int week) {
        // отчищаем список дней
        day.clear();

        // проверяем существует ли эта неделя
        if (week < 0)
            week = 0;
        if (week >= ((Week[]) Parametrs.getParam("weeks")).length)
            week = ((Week[]) Parametrs.getParam("weeks")).length - 1;

        // добавляем в список дни если это нужно
        for (int i = 0;
             i < ((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().size(); i++) {
            Calendar calendar = Calendar.getInstance();
            // если указана текущая неделя и день уже прошел то он не показывается
            if (week == ((int) Parametrs.getParam("thisWeek"))) {
                if (Integer.parseInt(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i).getDate().substring(0, 2)) >=
                        calendar.get(Calendar.DAY_OF_MONTH)) {
                    day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
                } else if (i != 0) {
                    notFirstDay = true;
                }
            } else {
                // если неделя не равна текущей то показываем все дни
                day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
            }
        }

        ListView listView = view.findViewById(R.id.listItem);
        TimeTableAdapter adapter = new TimeTableAdapter(view.getContext(),
                day, week == ((int) Parametrs.getParam("thisWeek")));
        listView.setAdapter(adapter);
    }
}
