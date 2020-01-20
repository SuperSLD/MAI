package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        SportGroupAdapter adapter = new SportGroupAdapter(((SimpleTree<String>) Parametrs.getParam("sport")).getChildList());

        RecyclerView recyclerView = findViewById(R.id.table);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            setTitle("Спортивные секции");
            getSupportActionBar().setSubtitle(null);
            return super.onCreateOptionsMenu(menu);
    }
}