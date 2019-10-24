package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.SportGroupAdapter;
import com.raspisanie.mai.Adapters.StudOrgAdapret;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

public class StudOrgActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_org);

        StudOrgAdapret adapter = new StudOrgAdapret(
                getBaseContext(), ((SimpleTree<String>) Parametrs.getParam("studOrg")).getChildList());

        ListView listView = findViewById(R.id.table);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Творческие колективы и клубы");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
