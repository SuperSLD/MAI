package com.raspisanie.mai.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.Fragments.ExamsFragment;
import com.raspisanie.mai.Fragments.InformationFragment;
import com.raspisanie.mai.Fragments.SettingsFragment;
import com.raspisanie.mai.Fragments.TimeTableFragment;
import com.raspisanie.mai.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Week[] weeks;
    private SharedPreferences mSettings;

    private android.app.FragmentTransaction fTrans;
    private TimeTableFragment timeTableFragment;
    private InformationFragment informationFagment;
    private SettingsFragment settingsFragment;
    private ExamsFragment examsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        weeks = gson.fromJson(mSettings.getString("weeks", ""), Week[].class);
        Parametrs.setParam("weeks", weeks);

        setThisWeek();

        Parametrs.setParam("kurs", mSettings.getInt("kurs", -1));
        Parametrs.setParam("fac", mSettings.getInt("fac", -1));
        Parametrs.setParam("group", mSettings.getInt("group", -1));

        fTrans = getFragmentManager().beginTransaction();
        timeTableFragment = new TimeTableFragment();
        settingsFragment = new SettingsFragment();
        informationFagment = new InformationFragment();
        examsFragment = new ExamsFragment();

        fTrans.add(R.id.fragment, timeTableFragment).commit();

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_time_table:
                    fTrans.replace(R.id.fragment, timeTableFragment).commit();
                    break;
                case R.id.action_exams:
                    fTrans.replace(R.id.fragment, examsFragment).commit();
                    break;
                case R.id.action_map:
                    fTrans.replace(R.id.fragment, informationFagment).commit();
                    break;
                case R.id.action_setting:
                    fTrans.replace(R.id.fragment, settingsFragment).commit();
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.action_time_table);

        //вывод сообщения с последним нововведением после обновления
        if (!mSettings.getString("version", "").equals(getResources().getString(R.string.versionString))
            && mSettings.getString("version", "").length() > 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Приложение было обновлено")
                    .setMessage(
                            getResources().getString(R.string.versionString) + "\n\n" +
                            getResources().getString(R.string.versionMessage)
                    )
                    .setIcon(R.drawable.ic_book_24px)
                    .setCancelable(false)
                    .setNegativeButton("Понятно",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("version", getResources().getString(R.string.versionString));
        editor.apply();

        //TODO добавить проверку обновления и вынести диалоговое окно со списком нововведений
    }

    /**
     * Переопределение метода нажатия кнопки назад.
     */
    @Override
    public void onBackPressed() {
        // <><><><>
    }

    private void setThisWeek() {
        int thisWeek = 0;
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            String s = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." +
                    (Calendar.getInstance().get(Calendar.MONTH)+1) + "." + Calendar.getInstance().get(Calendar.YEAR);
            date = ft.parse(s);
        } catch (Exception ex) { ex.printStackTrace(); }
        for (int i = 0; i < weeks.length; i++) {
            Date top = null;
            Date down = null;
            try {
                top = ft.parse(weeks[i].getDate().split(" - ")[1]);
                down = ft.parse(weeks[i].getDate().split(" - ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((!date.before(down) && date.before(top))) thisWeek = i;
            if (date.compareTo(top) == 0 || date.compareTo(down) == 0) thisWeek = i;
        }
        Parametrs.setParam("thisWeek", thisWeek);
    }
}
