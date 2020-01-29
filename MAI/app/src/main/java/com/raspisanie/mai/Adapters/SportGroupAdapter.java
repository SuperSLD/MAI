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

import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для списка спортивных секций.
 */
public class SportGroupAdapter extends RecyclerView.Adapter<SportGroupAdapter.SportGroupItem> {

    private ArrayList<SimpleTree<String>> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class SportGroupItem extends RecyclerView.ViewHolder {

        private TextView titleText;
        private LinearLayout linearLayout;
        private View view;

        public SportGroupItem(@NonNull View itemView) {
            super(itemView);
            this.titleText = itemView.findViewById(R.id.tableText);
            this.linearLayout = itemView.findViewById(R.id.tableList);
            this.view = itemView;
        }

        /**
         * Передача параметров в view элементы.
         * @param tree дерево элементов.
         */
        public void bind(SimpleTree<String> tree) {
            titleText.setText(tree.getValue());

            SportGroupSubAdapter adapter = new SportGroupSubAdapter(view.getContext() ,tree.getChildList());

            LinearLayout newLinearLayout = new LinearLayout(view.getContext());
            newLinearLayout.setOrientation(LinearLayout.VERTICAL);

            newLinearLayout.removeAllViews();
            for (int j = 0 ; j < tree.getChildList().size(); j++)
                newLinearLayout.addView(adapter.getView(j, null, newLinearLayout));

            linearLayout.removeAllViews();
            linearLayout.addView(newLinearLayout);
        }
    }

    /**
     * Конструктор адаптера.
     * @param korpus список элементов для отображения.
     */
    public SportGroupAdapter(ArrayList korpus) {
        this.objects = korpus;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param i позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public SportGroupItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sport_group, viewGroup, false);
        return new SportGroupAdapter.SportGroupItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param sportGroupItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull SportGroupItem sportGroupItem, int i) {
        sportGroupItem.bind(objects.get(i));
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