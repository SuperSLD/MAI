package com.raspisanie.mai.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.raspisanie.mai.Adapters.TimeTable.ViewHolderFactory;
import com.raspisanie.mai.Classes.NewsManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.Classes.TimeTable.TimeTableManager;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.Fragments.ExamsFragment;
import com.raspisanie.mai.Fragments.InformationFragment;
import com.raspisanie.mai.Fragments.SettingsFragment;
import com.raspisanie.mai.Fragments.TimeTableFragment;
import com.raspisanie.mai.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Logger.getLogger("mailog").log(Level.INFO, "MainActivity start method onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(findViewById(R.id.toolbar_actionbar));
        }

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        NewsManager.getInstance().loadNews();
        setThisWeek();
        ViewHolderFactory.setMainContext(this);

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
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            getSupportActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        createUpdateMessage();

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("version", getResources().getString(R.string.versionString));
        editor.apply();

    }

    /**
     * Создание сообщения с описанием именений в последней весрии.
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void createUpdateMessage() {
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
    }

    /**
     * Определение номера текущей недели в списке.
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    public static void setThisWeek() {
        ArrayList<Week> weeks = TimeTableManager.getInstance().getWeeks();

        int thisWeek = 0;
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            String s = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." +
                    (Calendar.getInstance().get(Calendar.MONTH)+1) + "." + Calendar.getInstance().get(Calendar.YEAR);
            date = ft.parse(s);
        } catch (Exception ex) { ex.printStackTrace(); }
        for (int i = 0; i < weeks.size(); i++) {
            Date top = null;
            Date down = null;
            try {
                top = ft.parse(weeks.get(i).getDate().split(" - ")[1]);
                down = ft.parse(weeks.get(i).getDate().split(" - ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (((!date.before(down) && date.before(top)))
                    || (date.compareTo(top) == 0 || date.compareTo(down) == 0)) thisWeek = i;

            if (date.after(top)) {
                thisWeek = weeks.size();
            }
        }
        if (thisWeek < 0) thisWeek = 0;
        Logger.getLogger("mai_log").log(Level.INFO, "this week -> " + thisWeek);
        TimeTableManager.getInstance().setThisWeek(thisWeek);
    }

    /**
     * Открытие информации из карточки события.
     * @param eventCard
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    public void openEventInfo(EventCard eventCard) {
        Intent intent = new Intent(this, EventInfoActivity.class);
        Parametrs.setParam("eventInfoParam", eventCard);
        startActivity(intent);
    }

    public void setNextWeek() {
        timeTableFragment.setNextWeek();
    }
}