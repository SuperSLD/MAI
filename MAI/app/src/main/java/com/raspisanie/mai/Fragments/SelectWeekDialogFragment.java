package com.raspisanie.mai.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.raspisanie.mai.Adapters.TimeTable.SelectWeekAdapter;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

/**
 * Диалог для выбора недели.
 */
public class SelectWeekDialogFragment extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_select_week, null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ListView listView = v.findViewById(R.id.list);
        SelectWeekAdapter adapter = new SelectWeekAdapter(getContext(), (Week[]) Parametrs.getParam("weeks"));
        listView.setAdapter(adapter);

        return v;
    }
}
