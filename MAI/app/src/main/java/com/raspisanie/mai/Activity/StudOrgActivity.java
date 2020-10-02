package com.raspisanie.mai.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.SportGroupAdapter;
import com.raspisanie.mai.Adapters.StudOrgAdapret;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

public class StudOrgActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_org);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(findViewById(R.id.toolbar_actionbar));
        }

        if (OtherDataManager.getInstance().getStudentGroupList().size() > 0) {
            findViewById(R.id.errText).setVisibility(View.GONE);
            StudOrgAdapret adapter = new StudOrgAdapret(OtherDataManager.getInstance().getStudentGroupList());

            RecyclerView recyclerView = findViewById(R.id.table);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle("Студенческие объединения");
        getSupportActionBar().setSubtitle(null);
        return super.onCreateOptionsMenu(menu);
    }
}
