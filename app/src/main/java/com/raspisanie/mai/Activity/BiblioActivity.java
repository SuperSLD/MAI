package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.raspisanie.mai.R;

public class BiblioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Библиотеки");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
