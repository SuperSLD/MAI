package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.CreativeAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.InformationConnection.InformationConnection;
import com.raspisanie.mai.R;

import java.lang.reflect.Parameter;

public class CreativGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creariv_group);

        InformationConnection.sendInfoActivity(this.getClass(), "onCreate()");

        RecyclerView recyclerView = findViewById(R.id.table);
        CreativeAdapter adapter = new CreativeAdapter(
                ((SimpleTree<String>) Parametrs.getParam("creative")).getChildList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Творческие колективы и клубы");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
