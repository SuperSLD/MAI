package com.raspisanie.mai.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raspisanie.mai.Activity.BiblioActivity;
import com.raspisanie.mai.Activity.CampusMapActivity;
import com.raspisanie.mai.Activity.CreativGroupActivity;
import com.raspisanie.mai.Activity.FoodActivity;
import com.raspisanie.mai.Activity.SportGroupActivity;
import com.raspisanie.mai.Activity.StudOrgActivity;
import com.raspisanie.mai.InformationConnection.InformationConnection;
import com.raspisanie.mai.InformationConnection.InformationRename;
import com.raspisanie.mai.R;

@InformationRename(name = "InformationFragment")
public class InformationFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.information_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle(R.string.title_information);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, null);

        InformationConnection.sendInfoActivity(this.getClass(), "onCreateView()");

        /*view.findViewById(R.id.button0).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), CampusMapActivity.class);
            startActivity(intent);
        });*/
        view.findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), FoodActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.button2).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), BiblioActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.button00).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), SportGroupActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.button01).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), StudOrgActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.button02).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), CreativGroupActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().setTitle(R.string.app_name);
    }
}
