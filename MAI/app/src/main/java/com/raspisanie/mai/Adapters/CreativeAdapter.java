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

import com.raspisanie.mai.Classes.DataModels.CreativeGroupObject;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для списка творческих коллективов.
 */
public class CreativeAdapter extends RecyclerView.Adapter<CreativeAdapter.CreativeItem> {

    private ArrayList<CreativeGroupObject> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class CreativeItem extends RecyclerView.ViewHolder {

        private TextView text1;
        private TextView text2;
        private TextView text3;

        public CreativeItem(@NonNull View itemView) {
            super(itemView);
            this.text1 = itemView.findViewById(R.id.t0);
            this.text2 = itemView.findViewById(R.id.t1);
            this.text3 = itemView.findViewById(R.id.t2);
        }

        /**
         * Передача параметров в view элементы.
         * @param tree дерево элементов.
         */
        public void bind(CreativeGroupObject creativeGroupObject) {
            try {
                text1.setText(creativeGroupObject.getName());
                text2.setText(creativeGroupObject.getAdministrator());
                text3.setText(creativeGroupObject.getInformation());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Конструктор адаптера.
     * @param list список элементов для отображения.
     */
    public CreativeAdapter(ArrayList list) {
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
    public CreativeItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_creative_group, viewGroup, false);
        return new CreativeItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param creativeItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull CreativeItem creativeItem, int i) {
        creativeItem.bind(objects.get(i));
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