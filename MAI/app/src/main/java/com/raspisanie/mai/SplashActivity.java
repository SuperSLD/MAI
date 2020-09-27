package com.raspisanie.mai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.LoadInformationActivity;
import com.raspisanie.mai.Activity.LoadTimeTableActivity;
import com.raspisanie.mai.Activity.Open;
import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.NewsManager;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.TimeTable.EventCardListManager;
import com.raspisanie.mai.Classes.TimeTable.TimeTableManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.getLogger("mailog").log(Level.INFO, "start MAI app");
        setContentView(R.layout.activity_splash);

        SharedPreferences mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        new Thread(() -> {
            Realm.init(getBaseContext());
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .name("mai_data.realm")
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(realmConfiguration);
            TimeTableManager.init();
            OtherDataManager.init();
            NewsManager.init(mSettings);
            EventCardListManager.init(this);

            Parametrs.setParam("mSettings", mSettings);
            if (mSettings.getInt("group", -1) > -1) {
                Gson gson = new Gson();
                String text = mSettings.getString("groupInfo", "");
                SimpleTree<String> tree = gson.fromJson(text, SimpleTree.class);
                Parametrs.setParam("tree", tree);

                if (TimeTableManager.getInstance().getWeeks().size() > 0) {

                    if (mSettings.getString("sport", "").length() > 10) {
                        Parametrs.setParam("sport",
                                gson.fromJson(mSettings.getString("sport", ""), SimpleTree.class));
                        Parametrs.setParam("creative",
                                gson.fromJson(mSettings.getString("creative", ""), SimpleTree.class));
                        Parametrs.setParam("studOrg",
                                gson.fromJson(mSettings.getString("studOrg", ""), SimpleTree.class));
                        Parametrs.setParam("stol",
                                gson.fromJson(mSettings.getString("stol", ""), SimpleTree.class));
                        Parametrs.setParam("lib",
                                gson.fromJson(mSettings.getString("lib", ""), SimpleTree.class));

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Intent intent = new Intent(SplashActivity.this, LoadInformationActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                Intent intent = new Intent(SplashActivity.this, LoadTimeTableActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            Intent intent = new Intent(this, Open.class);
            startActivity(intent);
            finish();
        }).start();
    }

    /**
     * Переопределение метода нажатия кнопки назад.
     */
    @Override
    public void onBackPressed() {
        // <><><><>
    }
}
