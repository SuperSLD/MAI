package com.raspisanie.mai.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Adapters.ExamTableAdapter;
import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.TimeTable.Subject;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

import org.jsoup.Jsoup;

public class ExamsFragment extends android.app.Fragment {
    private View view;
    private SharedPreferences mSettings;
    private boolean loadEnd = false;

    final int MAX_COUNT = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle(R.string.title_exams);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSettings = getActivity().getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_exams, null);

            if (mSettings.getString("examWeek", "").length() > 10
                    && new Gson().fromJson(mSettings.getString("examWeek", ""), Week.class)
                        .getDaysList().size() > 0) {
                setWeekInListView(view);
                updateExamList();
            } else {
                updateExamList();
            }
        }

        if (loadEnd) {
            setWeekInListView(view);
        }

        return view;
    }

    private void setWeekInListView(View view) {
        Gson gson = new Gson();
        view.findViewById(R.id.progresLayout).setVisibility(View.GONE);
        Week examWeek = gson.fromJson(mSettings.getString("examWeek", ""), Week.class);

        if (examWeek.getDaysList().size() > 0) {
            RecyclerView recyclerView = view.findViewById(R.id.examList);

            ExamTableAdapter adapter = new ExamTableAdapter(examWeek.getDaysList());

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
            recyclerView.setAdapter(adapter);
        } else {
            ((TextView) view.findViewById(R.id.errText)).setText(R.string.exam_fragment_error_string);
            view.findViewById(R.id.examsLayout).setVisibility(View.GONE);
        }
    }

    private void updateExamList() {
        if (new Gson().fromJson(mSettings.getString("examWeek", ""), Week.class) == null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("examWeek",
                    new Gson().toJson(new Week(0,"-")));
        }
        new Thread(() -> {
            try {
                URLSendRequest url = new URLSendRequest("https://mai.ru/", 50000);

                Week examsWeek = new Week(0, "-");

                String s = null;
                int countUrl = 0;

                while (s == null && countUrl < MAX_COUNT) {
                    s = url.get("education/schedule/session.php?group=" +
                            ((SimpleTree<String>) Parametrs.getParam("tree")).getChildList()
                                    .get(mSettings.getInt("kurs", -1)).getChildList()
                                    .get(mSettings.getInt("fac", -1)).getChildList()
                                    .get(mSettings.getInt("group", -1)).getValue());
                    countUrl++;
                }

                if (countUrl < MAX_COUNT) {
                    try {
                        String[] dayList = s.split("<div class=\"sc-table-col sc-day-header");
                        //Составление списка дней.
                        for (int j = 1; j < dayList.length; j++) {
                            Day day = new Day(
                                    dayList[j].split("<span")[0].split(">")[1],
                                    dayList[j].split("<span class=\"sc-day\">")[1].split("</")[0]
                            );

                            //Составление списка предметов.
                            String[] subjectList = dayList[j].split("<div class=\"sc-table-col sc-item-time\">");
                            for (int k = 1; k < subjectList.length; k++) {
                                String lect = "";
                                try {
                                    lect = subjectList[k].split("<span class=\"sc-lecturer\">")[1].split("<")[0];
                                } catch (Exception ex) {
                                }

                                String location = "";
                                try {
                                    location = subjectList[k].split("<div class=\"sc-table-col sc-item-location\">")[1].split("</div>")[0]
                                            .replaceAll("<br>", " - ");
                                    location = Jsoup.parse(location).text();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                Subject subject = new Subject(
                                        subjectList[k].split(" ")[0] + " - " +
                                                subjectList[k].split("<")[0].split(" ")[2],
                                        subjectList[k].split("table-col sc-item-type\">")[1].split("<")[0],
                                        subjectList[k].split("<span class=\"sc-title\">")[1].split("<")[0],
                                        lect,
                                        location
                                );
                                day.addSubject(subject);
                            }
                            examsWeek.addDay(day);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                }

                if (countUrl < MAX_COUNT) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("examWeek", gson.toJson(examsWeek));
                    editor.apply();
                }

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> setWeekInListView(view));
                } else {
                    loadEnd = true;
                }
            } catch (Exception ex) {

            }
        }).start();
    }
}
