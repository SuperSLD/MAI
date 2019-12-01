package com.raspisanie.mai.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Adapters.TimeTableAdapter;
import com.raspisanie.mai.Classes.TimeTable.TimeTableUpdater;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableFragment extends android.app.Fragment{
    View view;
    private View header;
    private View header2;

    private static boolean isUpdate = false;

    private final ArrayList<Day> day = new ArrayList<>();
    private boolean notFirstDay = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        SharedPreferences mSettings =
                getActivity().getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        //Обновление расписания в фоне
        if (!isUpdate && mSettings.getBoolean("updateChek", true)) {
            isUpdate = true;
            new Thread(() -> {
                if (TimeTableUpdater.update(mSettings)) {
                    Parametrs.setParam("weeks",
                            gson.fromJson(mSettings.getString("weeks", ""), Week[].class));
                }
            }).start();
        }
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
                if ((Integer.parseInt(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i).getDate().substring(0, 2)) >=
                        calendar.get(Calendar.DAY_OF_MONTH) &&
                        Integer.parseInt(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i).getDate().substring(3, 5)) ==
                        calendar.get(Calendar.MONTH) + 1)
                    || Integer.parseInt(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i).getDate().substring(3, 5)) >
                        calendar.get(Calendar.MONTH) + 1) {
                    day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
                } else {
                    notFirstDay = true;
                }
            } else {
                // если неделя не равна текущей то показываем все дни
                day.add(((Week[]) Parametrs.getParam("weeks"))[week].getDaysList().get(i));
            }
        }

        TimeTableAdapter adapter = new TimeTableAdapter(view.getContext(),
                day, week == ((int) Parametrs.getParam("thisWeek")));
        ListView listView = view.findViewById(R.id.listItem);
        // удаляемм заголовок списка
        listView.removeHeaderView(header);
        listView.removeHeaderView(header2);
        // если нужно устанавливаем новый заголовок
        if (notFirstDay && week == ((int) Parametrs.getParam("thisWeek")) ) {
            header = getActivity().getLayoutInflater().inflate(R.layout.header_time_table, null);
            header2 = getActivity().getLayoutInflater().inflate(R.layout.header_time_table_2, null);
            listView.addHeaderView(header);

            int finalWeek = week;
            int daySum = ((Week[]) Parametrs.getParam("weeks"))[finalWeek].getDaysList().size() - day.size() - 1;
            header.findViewById(R.id.buttonHeader).setOnClickListener(v -> {
                listView.removeHeaderView(header);
                for (int i = daySum; i >= 0; i--) {
                    day.add(0, ((Week[]) Parametrs.getParam("weeks"))[finalWeek].getDaysList().get(i));
                }
                listView.addHeaderView(header2);
                adapter.notifyDataSetChanged();
            });

            header2.findViewById(R.id.buttonHeader).setOnClickListener(v -> {
                listView.removeHeaderView(header2);
                for (int i = 0; i <= daySum; i++) {
                    day.remove(0);
                }
                listView.addHeaderView(header);
                adapter.notifyDataSetChanged();
            });
        }
        listView.setAdapter(adapter);
    }
}
