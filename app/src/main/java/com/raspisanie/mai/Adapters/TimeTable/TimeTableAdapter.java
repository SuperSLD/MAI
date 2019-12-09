package com.raspisanie.mai.Adapters.TimeTable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Адаптер для отображения расписания.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {

    private RecyclerView recyclerView;

    private boolean now;
    private boolean showAllDays;
    private ArrayList<Day> days;
    private ArrayList<Day> lastDays;

    private ArrayList<TimeTableViewHolder> dayViewHolders;

    /**
     * Конструктор адаптера.
     * @param days список элементов для отображения.
     */
    public TimeTableAdapter(ArrayList days, boolean now){
        this.now = now;
        this.days = days;
        this.lastDays = new ArrayList<>();

        this.showAllDays = false;

        if (now) {
            for (int i = 0; i < days.size(); i++) {
                Calendar calendar = Calendar.getInstance();
                // если указана текущая неделя и день уже прошел то он не показывается
                if (!((Integer.parseInt(this.days.get(i).getDate().substring(0, 2)) >=
                        calendar.get(Calendar.DAY_OF_MONTH) &&
                        Integer.parseInt(this.days.get(i).getDate().substring(3, 5)) ==
                                calendar.get(Calendar.MONTH) + 1)
                        || Integer.parseInt(this.days.get(i).getDate().substring(3, 5)) >
                        calendar.get(Calendar.MONTH) + 1)) {
                    this.lastDays.add(this.days.get(i));
                } else {
                    this.showAllDays = false;
                }
            }
            for (Day day : this.lastDays) this.days.remove(day);
        }

        this.dayViewHolders = new ArrayList<>();
    }

    /**
     * Получение родительского RecyclerView.
     * @param recyclerView родитель.
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * Определение типа элемента в зависимост от позиции.
     * @param position позиция элемента.
     * @return тип элемента.
     */
    @Override
    public int getItemViewType(int position) {
        if (!showAllDays) {
            return position == 0 ? 0 : 1;
        } else return  0;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param viewType позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        TimeTableViewHolder viewHolder = null;
        ViewHolderFactory.setNow(now);

        if (now) {
            switch (viewType) {
                case 0:
                    viewHolder = ViewHolderFactory.create(1, inflater, viewGroup);
                    break;
                case 1:
                    viewHolder = ViewHolderFactory.create(0, inflater, viewGroup);
                    break;
            }
        } else {
            viewHolder = ViewHolderFactory.create(0, inflater, viewGroup);
        }


        return viewHolder;
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param item эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder item, int i) {
        if (now) {
            if (i != 0) item.bind(days.get(i - 1));
        } else {
            item.bind(days.get(i));
        }
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
        if (now) {
            if (!showAllDays) {
                return days.size() + lastDays.size() + 1;
            } else return days.size();
        } else return days.size();
    }
}