package com.raspisanie.mai.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raspisanie.mai.Activity.LoadTimeTableActivity;
import com.raspisanie.mai.Activity.Open;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

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
        getActivity().setTitle(R.string.title_settings);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        SimpleTree<String> tree = (SimpleTree<String>) Parametrs.getParam("tree");
        String groupName = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                .getChildList().get((int)Parametrs.getParam("fac"))
                .getChildList().get((int)Parametrs.getParam("group")).getValue();
        String facName   = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                .getChildList().get((int)Parametrs.getParam("fac")).getValue();
        String kursName  = tree.getChildList().get((int)Parametrs.getParam("kurs"))
                .getValue();

        ((TextView) view.findViewById(R.id.textView1)).setText("Группа " + groupName);
        ((TextView) view.findViewById(R.id.textView2)).setText(facName);
        ((TextView) view.findViewById(R.id.textView3)).setText(kursName);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(getActivity().getBaseContext(), LoadTimeTableActivity.class);
                SharedPreferences.Editor editor = ((SharedPreferences) Parametrs.getParam("mSettings")).edit();
                editor.putString("weeks", "");
                editor.apply();
                startActivity(intent);
            }
        });
        view.findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(getActivity().getBaseContext(), Open.class);
                SharedPreferences.Editor editor = ((SharedPreferences) Parametrs.getParam("mSettings")).edit();
                editor.putString("weeks", "");
                editor.putString("groupInfo", "");
                editor.putInt("group", -1);
                editor.apply();
                startActivity(intent);
            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mai.ru"));
                startActivity(myIntent);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().setTitle(R.string.app_name);
    }
}
