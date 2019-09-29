package com.raspisanie.mai.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.List;

public class Open extends AppCompatActivity {

    private URLSendRequest url;
    private SimpleTree<String> tree = new SimpleTree<>("МАИ");
    private SharedPreferences mSettings;
    private int kurs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        final Spinner spinner = findViewById(R.id.multiAutoCompleteTextView);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        //если список был загружен то сразу переходим к выбору группы
        if (mSettings.getString("groupInfo", "").length() < 10) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    url = new URLSendRequest("https://mai.ru/", 50000);
                    String s = null;
                    while (s == null)
                    s = url.get("education/schedule/");

                    final ArrayList<String> list1 = new ArrayList<>();

                    //Собираем дерево групп.
                    String[] kurs = s.split("<h5 class=\"sc-container-header sc-gray\" >");
                    for (int i = 1; i < kurs.length; i++) {
                        //Находим курсы.
                        SimpleTree<String> k = new SimpleTree<>(kurs[i].split("</h5>")[0]);
                        list1.add(kurs[i].split("</h5>")[0]);
                        String[] fak = kurs[i].split("role=\"button\" data-toggle=\"collapse\" aria-expanded=\"false\" aria-controls=\"");
                        for (int j = 1; j < fak.length; j++) {
                            //Находим факультеты
                            SimpleTree<String> f = new SimpleTree<>(fak[j].split("</a>")[0].split(">")[1]);
                            String[] group = fak[j].split("<a class=\"sc-group-item\" href=\"");
                            for (int l = 1; l < group.length; l++) {
                                //Находим группы каждого факультета.
                                f.addChild(new SimpleTree<>(group[l].split("</a>")[0].split(">")[1]));
                            }
                            k.addChild(f);
                        }
                        tree.addChild(k);
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(tree);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("groupInfo", json);
                    editor.apply();

                    //убираем загрузку и показываем первый элемент с вводом
                    final ArrayAdapter adapter
                            = new ArrayAdapter(Open.this, android.R.layout.simple_dropdown_item_1line, list1.toArray());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar progressBar = findViewById(R.id.progressBar);
                            LinearLayout listView = findViewById(R.id.writeinfo);
                            TextView progressBarText = findViewById(R.id.textProgressBar);
                            progressBar.setVisibility(View.GONE);
                            progressBarText.setVisibility(View.GONE);

                            spinner.setAdapter(adapter);
                            listView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }).start();
        } else {
            Gson gson = new Gson();
            String text = mSettings.getString("groupInfo", "");
            tree = gson.fromJson(text, SimpleTree.class);

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < tree.getChildList().size(); i++) {
                list.add(tree.getChildList().get(i).getValue());
            }
            ArrayAdapter adapter =
                    new ArrayAdapter(Open.this, R.layout.support_simple_spinner_dropdown_item, list.toArray());

            ProgressBar progressBar = findViewById(R.id.progressBar);
            LinearLayout listView = findViewById(R.id.writeinfo);
            TextView progressBarText = findViewById(R.id.textProgressBar);
            progressBar.setVisibility(View.GONE);
            progressBarText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            spinner.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("selected item - " + i);
                kurs = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Open.this, SelectFacActivity.class);
                intent.putExtra("kurs", kurs);
                Parametrs.setParam("kurs", kurs);
                startActivity(intent);
            }
        });

        Parametrs.setParam("tree", tree);
    }
}
