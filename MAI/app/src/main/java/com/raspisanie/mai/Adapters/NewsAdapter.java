package com.raspisanie.mai.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raspisanie.mai.Classes.DataModels.NewsObject;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItem> {
    private ArrayList<NewsObject> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class NewsItem extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView text;
        private TextView date;

        public NewsItem(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.text = itemView.findViewById(R.id.text);
            this.date = itemView.findViewById(R.id.date);
        }

        /**
         * Передача параметров в view элементы.
         * @param newsObject новость.
         */
        public void bind(NewsObject newsObject) {
            title.setText(newsObject.getTitle());
            text.setText(newsObject.getText());
            date.setText(newsObject.getDate());
        }
    }

    /**
     * Конструктор адаптера.
     * @param news список элементов для отображения.
     */
    public NewsAdapter(ArrayList news) {
        this.objects = news;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param i позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public NewsAdapter.NewsItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_news_list, viewGroup, false);
        return new NewsAdapter.NewsItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param item эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsItem item, int i) {
        item.bind(objects.get(i));
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
