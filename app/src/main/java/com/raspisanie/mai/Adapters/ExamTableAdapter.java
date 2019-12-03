package com.raspisanie.mai.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.Classes.TimeTable.Week;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для списка экзаменов.
 */
public class ExamTableAdapter extends RecyclerView.Adapter<ExamTableAdapter.ExamItem> {

    private ArrayList<Day> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class ExamItem extends RecyclerView.ViewHolder {

        private View view;

        private View dayHeader;
        private TextView textDayName;
        private TextView textDayDate;
        private LinearLayout linearLayout;

        public ExamItem(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.dayHeader    = itemView.findViewById(R.id.dayHeader);
            this.textDayName  = itemView.findViewById(R.id.textView2);
            this.textDayDate  = itemView.findViewById(R.id.textView1);
            this.linearLayout = itemView.findViewById(R.id.listItem);
        }

        /**
         * Передача параметров в view элементы.
         * @param day день недели.
         */
        public void bind(Day day) {
            dayHeader.setBackgroundResource(R.color.colorPrimary);

            textDayDate.setText(day.getDate());
            textDayName.setText(day.getName());

            TimeTableSubAdapter adapter = new TimeTableSubAdapter(view.getContext(), day.getSubjectList());

            LinearLayout newLinearLayout = new LinearLayout(view.getContext());
            newLinearLayout.setOrientation(LinearLayout.VERTICAL);

            newLinearLayout.removeAllViews();
            for(int i = 0 ; i < day.getSubjectList().size(); i++)
                newLinearLayout.addView(adapter.getView(i, null, newLinearLayout));

            linearLayout.removeAllViews();
            linearLayout.addView(newLinearLayout);
        }
    }

    /**
     * Конструктор адаптера.
     * @param days список элементов для отображения.
     */
    public ExamTableAdapter(ArrayList days) {
        this.objects = days;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param i позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public ExamItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_table_main, viewGroup, false);
        return new ExamTableAdapter.ExamItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param examItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ExamItem examItem, int i) {
        examItem.bind(objects.get(i));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Количество элементов.
     */
    @Override
    public int getItemCount() {
        return objects.size();
    }
}
