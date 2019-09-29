package com.raspisanie.mai.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle(R.string.app_name);
    }
}
