package com.raspisanie.mai.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.TimeTable.Subject;
import com.raspisanie.mai.Classes.TimeTable.TimeTableUpdater;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

import org.jsoup.Jsoup;

import java.util.ArrayList;

public class LoadTimeTableActivity extends AppCompatActivity {
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_time_list);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        final TimeTableUpdater timeTableUpdater = new TimeTableUpdater();

        final boolean[] isUpdate = {false};
        new Thread(() -> {
            isUpdate[0] = timeTableUpdater.update(mSettings);
        }).start();
        new Thread(() -> {
            String lastProgress = "";
            while (true) {
                if (!lastProgress.equals(timeTableUpdater.getProgressString())) {
                    runOnUiThread(() -> ((TextView) findViewById(R.id.textViewProgress)).setText(
                            "Загружаем ваше расписание...\n" + timeTableUpdater.getProgressString()));
                }

                if (timeTableUpdater.isLoad() || isUpdate[0]) {
                    if (mSettings.getString("sport", "").length() > 10) {
                        Intent intent = new Intent(LoadTimeTableActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Intent intent = new Intent(LoadTimeTableActivity.this, LoadInformationActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                lastProgress = timeTableUpdater.getProgressString();
            }
        }).start();
    }

    /**
     * Переопределение метода нажатия кнопки назад
     */
    @Override
    public void onBackPressed() {
        // <><><><>
    }
}
