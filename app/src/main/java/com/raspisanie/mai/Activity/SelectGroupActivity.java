package com.raspisanie.mai.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.List;

public class SelectGroupActivity extends AppCompatActivity {

    private int kurs;
    private int fac;
    private int group = 0;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        mSettings = getSharedPreferences("appSettings",Context.MODE_PRIVATE);

        kurs = (int) Parametrs.getParam("kurs");
        fac = (int) Parametrs.getParam("fac");
        SimpleTree<String> tree = ((SimpleTree<String>) Parametrs.getParam("tree"))
                .getChildList().get(kurs).getChildList().get(fac);

        ArrayList<String> list = new ArrayList<>();
        for (SimpleTree<String> tr : tree.getChildList()) {
            list.add(tr.getValue());
        }

        ArrayAdapter adapter =
                new ArrayAdapter(SelectGroupActivity.this, R.layout.support_simple_spinner_dropdown_item, list.toArray());
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                group = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(SelectGroupActivity.this, LoadTimeTableActivity.class);
                intent.putExtra("group", group);
                Parametrs.setParam("group", group);

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt("group", group);
                editor.putInt("fac", fac);
                editor.putInt("kurs", kurs);
                editor.apply();

                startActivity(intent);
            }
        });

    }
}
