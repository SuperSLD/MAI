package com.raspisanie.mai.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.raspisanie.mai.Adapters.CanteenListAdapter;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.R;

public class CanteenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(findViewById(R.id.toolbar_actionbar));
        }

        if (OtherDataManager.getInstance().getCanteenList().size() > 0) {
            findViewById(R.id.errText).setVisibility(View.GONE);

            //Создание RecyclerView
            RecyclerView recyclerView = findViewById(R.id.list);
            CanteenListAdapter adapter =
                    new CanteenListAdapter(getBaseContext(),OtherDataManager.getInstance().getCanteenList());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle("Столовые и буфеты");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
