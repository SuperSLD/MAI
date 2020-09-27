package com.raspisanie.mai.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raspisanie.mai.Classes.DataModels.CanteenObject;
import com.raspisanie.mai.Classes.RealmModels.CanteenModel;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для списка столовых и буфетов.
 */
public class CanteenListAdapter extends RecyclerView.Adapter<CanteenListAdapter.StolItem> {
    private Context ctx;
    private ArrayList<CanteenObject> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class StolItem extends RecyclerView.ViewHolder {

        private TextView timeText;
        private TextView nameText;
        private TextView placeText;

        public StolItem(@NonNull View itemView) {
            super(itemView);
            this.timeText  = itemView.findViewById(R.id.timeText);
            this.nameText  = itemView.findViewById(R.id.t1);
            this.placeText = itemView.findViewById(R.id.t2);
        }

        /**
         * Передача параметров в view элементы.
         * @param object дерево элементов.
         */
        public void bind(CanteenObject object) {
            String time = "";
            try {
                time += object.getDate().replaceAll(" ", "\n");
            } catch (IndexOutOfBoundsException ex) {}
            timeText.setText(time);
            nameText.setText(object.getName());
            placeText.setText(object.getPlace());
        }
    }

    /**
     * Конструктор адаптера.
     * @param context контекст activity.
     * @param list список элементов для отображения.
     */
    public CanteenListAdapter(Context context, ArrayList list) {
        this.ctx = context;
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
    public StolItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_stol_list, viewGroup, false);
        return new StolItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param stolItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull StolItem stolItem, int i) {
        stolItem.bind(objects.get(i));
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
