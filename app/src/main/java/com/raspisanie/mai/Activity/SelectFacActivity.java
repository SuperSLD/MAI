package com.raspisanie.mai.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class SelectFacActivity extends AppCompatActivity {

    private SimpleTree<String> tree;
    private int fac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fac);

        Intent intent = getIntent();
        int kurs = intent.getIntExtra("kurs", 0);
        tree = ((SimpleTree<String>) Parametrs.getParam("tree")).getChildList().get(kurs);
        System.out.println(tree.toString(0));

        ArrayList<String> list = new ArrayList<>();
        for (SimpleTree<String> tr : tree.getChildList()) {
            list.add(tr.getValue());
        }

        ArrayAdapter adapter =
                new ArrayAdapter(SelectFacActivity.this, R.layout.support_simple_spinner_dropdown_item, list.toArray());
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fac = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(SelectFacActivity.this, SelectGroupActivity.class);
                intent.putExtra("fac", fac);
                Parametrs.setParam("fac", fac);
                startActivity(intent);
            }
        });
    }
}
