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

import com.raspisanie.mai.Classes.DataModels.StudentGroupObject;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для списка студенческиъ организаций.
 */
public class StudOrgAdapret extends RecyclerView.Adapter<StudOrgAdapret.StudOrgItem> {

    private ArrayList<StudentGroupObject> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class StudOrgItem extends RecyclerView.ViewHolder {

        private TextView text1;
        private TextView text2;
        private TextView text3;
        private TextView text4;

        public StudOrgItem(@NonNull View itemView) {
            super(itemView);
            this.text1 = itemView.findViewById(R.id.t0);
            this.text2 = itemView.findViewById(R.id.t1);
            this.text3 = itemView.findViewById(R.id.t2);
            this.text4 = itemView.findViewById(R.id.t3);

        }

        /**
         * Передача параметров в view элементы.
         * @param obj дерево элементов.
         */
        public void bind(StudentGroupObject obj) {
            text1.setText(obj.getName());
            text2.setText(obj.getAdministrator());
            text3.setText(obj.getPhoneNumber());
            text4.setText(obj.getInformation());
        }
    }

    /**
     * Конструктор адаптера.
     * @param list список элементов для отображения.
     */
    public StudOrgAdapret(ArrayList list) {
        this.objects = list;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param i позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public StudOrgItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_stud_org, viewGroup, false);
        return new StudOrgAdapret.StudOrgItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param studOrgItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull StudOrgItem studOrgItem, int i) {
        studOrgItem.bind(objects.get(i));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
