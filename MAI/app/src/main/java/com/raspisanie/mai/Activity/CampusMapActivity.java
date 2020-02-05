package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.raspisanie.mai.R;

/**
 * Схема кампуса.
 */
public class CampusMapActivity  extends AppCompatActivity {

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