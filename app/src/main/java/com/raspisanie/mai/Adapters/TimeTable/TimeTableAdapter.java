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

import javax.xml.transform.sax.TemplatesHandler;

/**
 * Адаптер для отображения расписания.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {

    private RecyclerView recyclerView;

    private boolean now;
    private boolean notFirstDay;
    private boolean showAllDays;
    private ArrayList<Day> startDayList;
    private ArrayList<Day> day;
    private int daySum;

    /**
     * Конструктор адаптера.
     *
     * @param day1 список элементов для отображения.
     */
    public TimeTableAdapter(ArrayList day1, boolean now) {
        this.now = now;
        this.notFirstDay = false;
        this.showAllDays = false;

        ArrayList<Day> day = (ArrayList<Day>) day1;
        startDayList = day;
        this.day =  new ArrayList<>();

        for (int i = 0; i < day.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            // если указана текущая неделя и день уже прошел то он не показывается
            if (now) {
                if ((Integer.parseInt(day.get(i).getDate().substring(0, 2)) >=
                        calendar.get(Calendar.DAY_OF_MONTH) &&
                        Integer.parseInt(day.get(i).getDate().substring(3, 5)) ==
                                calendar.get(Calendar.MONTH) + 1)
                        || Integer.parseInt(day.get(i).getDate().substring(3, 5)) >
                        calendar.get(Calendar.MONTH) + 1) {
                    this.day.add(day.get(i));
                } else {
                    notFirstDay = true;
                }
            } else {
                // если неделя не равна текущей то показываем все дни
                this.day.add(day.get(i));
            }
        }
        daySum = day.size() - this.day.size() - 1;
    }

    /**
     * Получение родительского RecyclerView.
     *
     * @param recyclerView родитель.
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * Определение типа элемента в зависимост от позиции.
     *
     * @param position позиция элемента.
     * @return тип элемента.
     */
    @Override
    public int getItemViewType(int position) {
        if (now) {
            if (position == 0 && notFirstDay) {
                return showAllDays ? 2 : 1;
            } else {
                return 0;
            }
        } else return 0;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     *
     * @param viewGroup
     * @param viewType  позиция элемениа.
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
                    viewHolder = ViewHolderFactory.create(0, inflater, viewGroup);
                    break;
                case 1:
                    viewHolder = ViewHolderFactory.create(1, inflater, viewGroup);
                    ((HeaderOpenItem) viewHolder).setOnClickListener(v -> {
                        showAllDays = true;
                        notifyItemChanged(0);
                        updateDayList();
                    });
                    break;
                case 2:
                    viewHolder = ViewHolderFactory.create(2, inflater, viewGroup);
                    ((HeaderCloseItem) viewHolder).setOnClickListener(v -> {
                        showAllDays = false;
                        notifyItemChanged(0);
                        updateDayList();
                    });
                    break;
            }
        } else {
            viewHolder = ViewHolderFactory.create(0, inflater, viewGroup);
        }


        return viewHolder;
    }

    /**
     * Обновление элементов списка.
     */
    private void updateDayList() {
        if (showAllDays) {
            for (int i = daySum; i >= 0; i--) {
                day.add(0, (startDayList.get(i)));
                notifyItemInserted(i);
            }
        } else {
            for (int i = daySum; i >= 0; i--) {
                day.remove(0);
                notifyItemRemoved(i + 1);
            }
        }
    }

    /**
     * Передача параметров в элемент списка итемов.
     *
     * @param item эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder item, int i) {
        if (now && notFirstDay) {
            if (i != 0) item.bind(day.get(i - 1));
        } else item.bind(day.get(i));
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
            if (notFirstDay) {
                return day.size() + 1;
            } else {
                return day.size();
            }
        } else return day.size();
    }
}