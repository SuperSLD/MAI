package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.CreativeAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.lang.reflect.Parameter;

public class CreativGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creariv_group);

        ListView listView = findViewById(R.id.table);
        CreativeAdapter adapter = new CreativeAdapter(getBaseContext(),
                ((SimpleTree<String>) Parametrs.getParam("creative")).getChildList());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Творческие колективы и клубы");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
