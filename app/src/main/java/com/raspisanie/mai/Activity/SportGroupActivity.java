package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.SportGroupAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

public class SportGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_group);

        SportGroupAdapter adapter = new SportGroupAdapter(
                getBaseContext(), ((SimpleTree<String>) Parametrs.getParam("sport")).getChildList());

        ListView listView = findViewById(R.id.table);
        listView.setAdapter(adapter);
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            setTitle("Спортивные секции");
            getSupportActionBar().setSubtitle(null);
            return super.onCreateOptionsMenu(menu);
    }
}