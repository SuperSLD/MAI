package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.LibAdapter;
import com.raspisanie.mai.Adapters.StolListAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

public class BiblioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblio);

        if (Parametrs.getParam("lib") != null) {
            findViewById(R.id.errText).setVisibility(View.GONE);
            ListView listView = findViewById(R.id.list);
            LibAdapter adapter =
                    new LibAdapter(getBaseContext(),
                            ((SimpleTree<String>) Parametrs.getParam("lib")).getChildList());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Библиотеки");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}