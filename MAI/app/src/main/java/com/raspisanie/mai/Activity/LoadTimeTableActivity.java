package com.raspisanie.mai.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.raspisanie.mai.Classes.TimeTable.TimeTableManager;
import com.raspisanie.mai.R;

public class LoadTimeTableActivity extends AppCompatActivity {
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_time_list);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        final TimeTableManager timeTableManager = TimeTableManager.getInstance();

        final boolean[] isUpdate = {false};
        new Thread(() -> {
            isUpdate[0] = timeTableManager.update(mSettings);
        }).start();
        new Thread(() -> {
            String lastProgress = "";
            while (true) {
                if (!lastProgress.equals(timeTableManager.getProgressString())) {
                    runOnUiThread(() -> ((TextView) findViewById(R.id.textViewProgress)).setText(
                            "Загружаем ваше расписание...\n" + timeTableManager.getProgressString()));
                }

                if (timeTableManager.isLoad() || isUpdate[0]) {
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
                lastProgress = timeTableManager.getProgressString();
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
