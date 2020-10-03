package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.R;

/**
 * Фабрика TimeTableViewHolder объектов.
 */
public class ViewHolderFactory {

    /**
     * Параметры необходимый для игициализации некоторых объектов.
     */
    private static boolean now;
    private static TimeTableAdapter timeTableAdapter;
    private static MainActivity activity;

    /**
     * Возвращает объект в зависимости от входного кода.
     * @return TimeTableViewHolder элемент.
     */
    public static TimeTableViewHolder create(int code, LayoutInflater inflater, ViewGroup viewGroup) {
        View view = null;
        switch (code) {
            case 0:
                view = inflater.inflate(R.layout.item_table_main, viewGroup, false);
                return new TimeTableItem(view, now);
            case 1:
                view = inflater.inflate(R.layout.header_time_table, viewGroup, false);
                return new HeaderOpenItem(view);
            case 2:
                view = inflater.inflate(R.layout.header_time_table_2, viewGroup, false);
                return new HeaderCloseItem(view);
            case 3:
                view = inflater.inflate(R.layout.item_events_card, viewGroup, false);
                return new EventCardItem(view, viewGroup.getContext(), timeTableAdapter, activity);
            case 4:
                view = inflater.inflate(R.layout.item_next_week, viewGroup, false);
                return new NextWeekItem(view, activity);
            default:
                return null;
        }
    }

    /**
     * Установка параметра Now.
     * @param bol
     */
    public static void setNow(boolean bol) {
        now = bol;
    }

    /**
     * Установка адаптера расписания.
     * @param timeTableAdapter
     */
    public static void setTimeTableAdapter(TimeTableAdapter timeTableAdapter) {
        ViewHolderFactory.timeTableAdapter = timeTableAdapter;
    }

    /**
     * Установка контекста главной активноси.
     * @param activity
     */
    public static void setMainContext(MainActivity activity) {
        ViewHolderFactory.activity = activity;
    }
}
