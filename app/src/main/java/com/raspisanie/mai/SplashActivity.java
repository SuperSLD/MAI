package com.raspisanie.mai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.LoadTimeTableActivity;
import com.raspisanie.mai.Activity.Open;
import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Сделать красивую заставку (SVG)

        SharedPreferences mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        Parametrs.setParam("mSettings", mSettings);
        if (mSettings.getInt("group", -1) > -1) {
            Gson gson = new Gson();
            String text = mSettings.getString("groupInfo", "");
            SimpleTree<String> tree = gson.fromJson(text, SimpleTree.class);
            Parametrs.setParam("tree", tree);


            if (mSettings.getString("weeks", "").length() > 10) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
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
}
