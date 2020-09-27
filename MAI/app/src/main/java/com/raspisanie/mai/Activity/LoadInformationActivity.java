package com.raspisanie.mai.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.R;

import org.jsoup.Jsoup;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;

public class LoadInformationActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private SimpleTree<String> sport = new SimpleTree<>("Виды спорта");
    private SimpleTree<String> creative = new SimpleTree<>("Творческие коллективы");
    private SimpleTree<String> studOrg = new SimpleTree<>("Студенческие органмзации");
    private SimpleTree<String> stolTree = new SimpleTree<>("Столовые");
    private SimpleTree<String> libTree = new SimpleTree<>("Библиотеки");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_information);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        new Thread(() -> {
            OtherDataManager.getInstance().loadInformation(this);

            Intent intent = new Intent(LoadInformationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }).start();
    }

    /**
     * Установка текста на TextView под прогрес баром.
     * @param s текст который будет под прогрес баром.
     */
    public void setProgressText(String s) {
        runOnUiThread(() -> ((TextView) findViewById(R.id.textViewProgress)).setText(s));
    }

    /**
     * Переопределение метода нажатия кнопки назад
     */
    @Override
    public void onBackPressed() {
        // <><><><>
    }
}