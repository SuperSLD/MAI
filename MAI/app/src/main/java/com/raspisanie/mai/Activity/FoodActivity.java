package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.StolListAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.InformationConnection.InformationConnection;
import com.raspisanie.mai.R;

public class FoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stol_list);

        InformationConnection.sendInfoActivity(this.getClass(), "onCreate()");

        if (Parametrs.getParam("stol") != null) {
            findViewById(R.id.errText).setVisibility(View.GONE);

            //Создание RecyclerView
            RecyclerView recyclerView = findViewById(R.id.list);
            StolListAdapter adapter =
                    new StolListAdapter(getBaseContext(),
                            ((SimpleTree<String>) Parametrs.getParam("stol")).getChildList());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Столовые и буфеты");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
