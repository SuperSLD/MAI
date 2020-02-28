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
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.TimeTable.EventCardListManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.getLogger("mailog").log(Level.INFO, "start MAI app");
        //TODO Сделать красивую заставку (SVG)

        SharedPreferences mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        EventCardListManager.initList(mSettings);

        Parametrs.setParam("mSettings", mSettings);
        if (mSettings.getInt("group", -1) > -1) {
            Gson gson = new Gson();
            String text = mSettings.getString("groupInfo", "");
            SimpleTree<String> tree = gson.fromJson(text, SimpleTree.class);
            Parametrs.setParam("tree", tree);

            if (mSettings.getString("weeks", "").length() > 10) {

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
                    return;
                }
                Intent intent = new Intent(SplashActivity.this, LoadInformationActivity.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(SplashActivity.this, LoadTimeTableActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(this, Open.class);
        startActivity(intent);
        finish();
    }

    /**
     * Переопределение метода нажатия кнопки назад.
     */
    @Override
    public void onBackPressed() {
        // <><><><>
    }
}
