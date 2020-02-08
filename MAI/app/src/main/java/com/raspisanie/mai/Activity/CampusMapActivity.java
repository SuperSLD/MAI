package com.raspisanie.mai.Activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.raspisanie.mai.R;

import java.io.InputStream;

/**
 * Схема кампуса.
 */
public class CampusMapActivity  extends AppCompatActivity {

    private String data;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Общая схема кампуса");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map);

    }
}