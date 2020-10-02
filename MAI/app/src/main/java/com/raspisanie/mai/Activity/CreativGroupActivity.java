package com.raspisanie.mai.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.CreativeAdapter;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.lang.reflect.Parameter;

public class CreativGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creariv_group);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(findViewById(R.id.toolbar_actionbar));
        }

        if (OtherDataManager.getInstance().getCreativeGroupList().size() > 0) {
            findViewById(R.id.errText).setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.table);
            CreativeAdapter adapter = new CreativeAdapter(
                    OtherDataManager.getInstance().getCreativeGroupList());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle("Творческие колективы и клубы");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
