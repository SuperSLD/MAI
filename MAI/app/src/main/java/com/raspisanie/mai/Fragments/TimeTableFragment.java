package com.raspisanie.mai.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.InformationConnection.InformationConnection;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Adapters.TimeTable.TimeTableAdapter;
import com.raspisanie.mai.Classes.TimeTable.EventCardListManager;
import com.raspisanie.mai.Classes.TimeTable.TimeTableUpdater;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.InformationConnection.InformationRename;
import com.raspisanie.mai.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@InformationRename(name = "TimeTableFragment")
public class TimeTableFragment extends android.app.Fragment{
    View view;
    private static boolean isUpdate = false;
    private TimeTableAdapter adapter;
    private SharedPreferences mSettings;
    private boolean update = false;

    private int selectWeek;
    private TimeTableUpdater timeTableUpdater;

    private MenuItem next;
    private MenuItem prev;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        timeTableUpdater = new TimeTableUpdater();
        mSettings =
                getActivity().getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        //Обновление расписания раз в день.
        if (!isUpdate && mSettings.getBoolean("updateChek", true)) {
            isUpdate = true;
            if(!mSettings.getString("lastUpdate", "").equals(
                new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()))) {
                new Thread(() -> {
                    update = timeTableUpdater.update(mSettings);
                    if (update) {
                        Parametrs.setParam("weeks",
                                gson.fromJson(mSettings.getString("weeks", ""), Week[].class));
                    }
                }).start();
            } else timeTableUpdater.setLoadStatus(true);
            if(!mSettings.getString("lastUpdateEvents", "").equals(
                new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime())))
                new Thread(() -> {
                    EventCardListManager.eventCardListUpdate(mSettings);
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
                setDaysList(Parametrs.getInt("thisWeek") - 1);
                selectWeek = Parametrs.getInt("thisWeek") - 1;
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Предыдущая неделя");
                item.setVisible(false);
                prev = item;
                if (next != null) next.setVisible(true);
                break;
            case R.id.but2:
                setDaysList(Parametrs.getInt("thisWeek"));
                selectWeek = Parametrs.getInt("thisWeek");
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Текущая неделя");
                if (next != null) next.setVisible(true);
                if (prev != null) prev.setVisible(true);
                break;
            case R.id.but3:
                setDaysList(Parametrs.getInt("thisWeek") + 1);
                selectWeek = Parametrs.getInt("thisWeek") + 1;
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Следующая неделя");
                item.setVisible(false);
                if (prev != null) prev.setVisible(true);
                next = item;
                break;
            case R.id.but4:
                SelectWeekDialogFragment dialog = new SelectWeekDialogFragment();
                dialog.setButtonAction(() -> {
                    setDaysList(dialog.getPosition());
                    selectWeek = dialog.getPosition();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(
                            ((Week[]) Parametrs.getParam("weeks"))[dialog.getPosition()].getDate()
                    );
                    if (next != null) next.setVisible(true);
                    if (prev != null) prev.setVisible(true);
                });
                dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "tag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectWeek = Parametrs.getInt("thisWeek");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_time_table, null);
            setDaysList((int) Parametrs.getParam("thisWeek"));
        }

        InformationConnection.sendInfoActivity(this.getClass(), "onCreateView()");

        return view;
    }

    /**
     * @author Леонид Соляной (solyanoy.leonid@gmail.com)
     *
     * Передаем в ListView adapter с списком дней текущей недели.
     * Сам список дней объявлен глобальным чтоб его можно было динамически изменять.
     * @param week номер текущей недели.
     */
    public void setDaysList(int week) {
        LinearLayout linearLayout = view.findViewById(R.id.falseWeek);
        RecyclerView recyclerView = view.findViewById(R.id.listItem);

        Week w = null;
        if (week < 0) week = 0;
        try {
            w = ((Week[]) Parametrs.getParam("weeks"))[week];
        } catch (IndexOutOfBoundsException ex) {}

        if (w != null) {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TimeTableAdapter(
                    w.getDaysList(), week,
                    week == ((int) Parametrs.getParam("thisWeek"))
            );
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
            recyclerView.setAdapter(adapter);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            ProgressBar progressBar = view.findViewById(R.id.progress);
            ImageView imageView     = view.findViewById(R.id.image);
            TextView textView       = view.findViewById(R.id.infoText);

            if (!timeTableUpdater.isLoad()) {
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                textView.setText("Загрузка");

                new Thread(() -> {
                    try {
                        String progr = "";
                        while (!timeTableUpdater.isLoad()) {
                            if (!progr.equals(timeTableUpdater.getProgressString())) {
                                getActivity().runOnUiThread(() ->
                                        textView.setText("Загрузка\n" + timeTableUpdater.getProgressString()));
                            }
                            progr = timeTableUpdater.getProgressString();
                        }
                        getActivity().runOnUiThread(() -> {
                            if (!update) {
                                progressBar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                textView.setText("Расписание отсутствует");
                            } else {
                                if (timeTableUpdater.isNewWeekList()) {
                                    Week[] weeks = new Gson().fromJson(mSettings.getString("weeks", ""), Week[].class);
                                    Parametrs.setParam("weeks", weeks);
                                    MainActivity.setThisWeek();
                                    setDaysList(Parametrs.getInt("thisWeek"));
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    textView.setText("Расписание отсутствует");
                                }
                            }
                        });
                    } catch (Exception ex) {}
                }).start();
            } else {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                textView.setText("Расписание отсутствует");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDaysList(selectWeek);
    }
}
