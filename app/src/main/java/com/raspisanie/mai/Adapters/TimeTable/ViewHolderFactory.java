package com.raspisanie.mai.Adapters.TimeTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raspisanie.mai.R;

import java.util.zip.Inflater;

/**
 * Фабрика TimeTableViewHolder объектов.
 */
public class ViewHolderFactory {

    /**
     * Параметр необходимый для игициализации некоторых объектов.
     */
    private static boolean now;

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
}
