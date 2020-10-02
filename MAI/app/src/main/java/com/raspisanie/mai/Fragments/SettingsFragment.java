package com.raspisanie.mai.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.raspisanie.mai.Activity.LoadInformationActivity;
import com.raspisanie.mai.Activity.LoadTimeTableActivity;
import com.raspisanie.mai.Activity.NewsActivity;
import com.raspisanie.mai.Activity.Open;
import com.raspisanie.mai.Classes.OtherDataManager;
import com.raspisanie.mai.Classes.RealmModels.WeekModel;
import com.raspisanie.mai.Classes.TimeTable.EventCardListManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.TimeTable.TimeTableManager;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;
import com.raspisanie.mai.View.DiagramView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;

public class SettingsFragment extends android.app.Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_settings);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);
        SharedPreferences mSettings = getActivity().getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        String groupName = "default";
        String facName = "default";
        String kursName = "default";
        try {
            SimpleTree<String> tree = (SimpleTree<String>) Parametrs.getParam("tree");
            groupName = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                    .getChildList().get((int)Parametrs.getParam("fac"))
                    .getChildList().get((int)Parametrs.getParam("group")).getValue();
            facName   = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                    .getChildList().get((int)Parametrs.getParam("fac")).getValue();
            kursName  = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                    .getValue();

        } catch (IndexOutOfBoundsException ex) {

        }
        ((TextView) view.findViewById(R.id.textView1)).setText(
                getResources().getString(R.string.fragment_settings_title2)  + " "
                + groupName
        );
        ((TextView) view.findViewById(R.id.textView2)).setText(facName);
        ((TextView) view.findViewById(R.id.textView3)).setText(kursName);



        //Обработчики нажатий на кнопки под информацией
        view.findViewById(R.id.button).setOnClickListener(v -> {
            reloadTimeTable();
        });
        view.findViewById(R.id.button0).setOnClickListener(v -> {
            reloadUserInformation();
        });
        view.findViewById(R.id.button3).setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mai.ru"));
            startActivity(myIntent);
        });
        view.findViewById(R.id.button01).setOnClickListener(v -> {
            reloadInformation();
        });
        view.findViewById(R.id.seteventsrtate).setOnClickListener(v -> {
            reloadEvents();
        });
        view.findViewById(R.id.appnews).setOnClickListener(v -> {
            openNewsList();
        });

        //устнвка данных об фоновом обновлении расписания
        ((TextView) view.findViewById(R.id.textView101)).setText(mSettings.getString("lastUpdate", "---"));
        CheckBox checkBox = view.findViewById(R.id.checkbox03);
        checkBox.setChecked(mSettings.getBoolean("updateChek", true));
        checkBox.setOnCheckedChangeListener((c, b) -> {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean("updateChek", b);
            editor.apply();
        });

        checkBox = view.findViewById(R.id.checkbox01);
        checkBox.setChecked(mSettings.getBoolean("eventCheck", true));
        checkBox.setOnCheckedChangeListener((c, b) -> {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean("eventCheck", b);
            editor.apply();
        });

        //Установка параметров диаграммы
        float[] data = new float[3];
        try {
            data[0] = TimeTableManager.getInstance().getWeeks().toString().getBytes("UTF-8").length;
            data[1] = mSettings.getString("groupInfo", "").getBytes("UTF-8").length;
            data[2] =
                    (OtherDataManager.getInstance().getSportGroupList().toString().getBytes("UTF-8").length) +
                    (OtherDataManager.getInstance().getCanteenList().toString().getBytes("UTF-8").length) +
                    (OtherDataManager.getInstance().getCreativeGroupList().toString().getBytes("UTF-8").length) +
                    (OtherDataManager.getInstance().getLibraryList().toString().getBytes("UTF-8").length) +
                    (OtherDataManager.getInstance().getStudentGroupList().toString().getBytes("UTF-8").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.textViewD0)).setText(String.format("Расписание %s KB",  Integer.toString((int)data[0]/1024)));
        ((TextView) view.findViewById(R.id.textViewD1)).setText(String.format("Информация о группах %s KB", Integer.toString((int)data[1]/1024)));
        ((TextView) view.findViewById(R.id.textViewD2)).setText(String.format("Информация о ВУЗе %s KB", Integer.toString((int)data[2]/1024)));

        DiagramView diagramView = view.findViewById(R.id.diagram1);
        diagramView.setCenterText("Устройство");
        diagramView.setCenterSubText((int) ((data[0] + data[1] + data[2])/1024) + " KB");
        diagramView.setColorText(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.diagramSubText));
        diagramView.setData(data,
                new int[]{
                        getResources().getColor(R.color.diagram1),
                        getResources().getColor(R.color.diagram2),
                        getResources().getColor(R.color.diagram3)
                });

        return view;
    }

    /**
     * Открытие списка новостей приложения.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void openNewsList() {
        Intent intent =
                new Intent(getActivity().getBaseContext(), NewsActivity.class);
        startActivity(intent);
    }

    /**
     * Перезагрузка информации о группе и всей остальной информации.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void reloadUserInformation() {
        Intent intent =
                new Intent(getActivity().getBaseContext(), Open.class);
        SharedPreferences.Editor editor = ((SharedPreferences) Parametrs.getParam("mSettings")).edit();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(WeekModel.class);
        editor.putString("groupInfo", "");
        editor.putInt("group", -1);
        editor.apply();
        realm.commitTransaction();
        startActivity(intent);
    }

    /**
     * Перезагрущка расписания.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void reloadTimeTable() {
        Intent intent =
                new Intent(getActivity().getBaseContext(), LoadTimeTableActivity.class);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(WeekModel.class);
        realm.commitTransaction();
        startActivity(intent);
    }

    /**
     * Перезагрзка информации о вузе.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void reloadInformation() {
        Intent intent =
                new Intent(getActivity().getBaseContext(), LoadInformationActivity.class);
        startActivity(intent);
    }

    /**
     * Возвращение всех скрытых событий.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void reloadEvents() {
        EventCardListManager.getInstance().unarchiveEventList();
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().setTitle(R.string.app_name);
    }
}
