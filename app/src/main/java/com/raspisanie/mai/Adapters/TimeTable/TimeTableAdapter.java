package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Адаптер для отображения расписания.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {

    private boolean now;
    private boolean notFirstDay;
    private boolean showAllDays;
    private ArrayList<Day> startDayList;
    private ArrayList<Object> day;
    private int daySum;

    /**
     * Конструктор адаптера.
     *
     * @param day1 список элементов для отображения.
     */
    public TimeTableAdapter(ArrayList day1, boolean now, Context context) {
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

        this.day.add(1,new EventCard("Вуз Пром Экспо",
                "17.12.2019",
                EventCard.decodeSampledBitmapFromResource(
                        context.getResources(), R.drawable.test_image, 100, 100)));
    }

    /**
     * Определение типа элемента в зависимост от позиции.
     *
     * @param position позиция элемента.
     * @return тип элемента.
     */
    @Override
    public int getItemViewType(int position) {
        //TODO при возможности поправить и сделать короче.
        if (now) {
            if (position == 0 && notFirstDay) {
                return showAllDays ? 2 : 1;
            } else {
                if (notFirstDay) position--;
                if (day.get(position) instanceof Day) {
                    return 0;
                } else if (day.get(position) instanceof EventCard) {
                    return 3;
                } else {
                    return -1;
                }
            }
        } else {
            if (day.get(position) instanceof Day) {
                return 0;
            } else if (day.get(position) instanceof EventCard) {
                return 3;
            } else {
                return -1;
            }
        }
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
        ViewHolderFactory.setTimeTableAdapter(this);

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
                    /*insertItem(new EventCard("Вуз Пром Экспо",
                            "13.12.2019",
                            EventCard.decodeSampledBitmapFromResource(
                            viewGroup.getContext().getResources(), R.drawable.test_image, 100, 100)), 0);*/
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
            case 3:
                viewHolder = ViewHolderFactory.create(3, inflater, viewGroup);
                TimeTableViewHolder finalViewHolder = viewHolder;
                break;
        }

        return viewHolder;
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

    /**
     * Добавление и удаление скрытых дней.
     */
    private void updateDayList() {
        if (showAllDays) {
            for (int i = daySum; i >= 0; i--) {
                insertItem(startDayList.get(i), 0);
            }
        } else {
            for (int i = daySum; i >= 0; i--) {
                deleteItem(0);
            }
        }
    }

    /**
     * Вставка объекта с анимацией.
     * @param obj объект.
     * @param i позиция для нового объекта.
     */
    public void insertItem(Object obj, int i) {
        if (i < day.size()) {
            day.add(i, obj);
        } else {
            day.add(obj);
            i = day.size() - 1;
        }
        notifyItemInserted(i);
    }

    /**
     * Удаление объекта с анимацией.
     * @param i позиция удаляемного элемента.
     */
    public void deleteItem(int i) {
        day.remove(i);
        notifyItemRemoved(i);
    }

    /**
     * Удаление EventCard с анимацией.
     * @param ID идентификатор элемента.
     */
    public void deleteEventCardByID(int ID) {
        for (int i = 0; i < day.size(); i++) {
            if (day.get(i) instanceof EventCard && ((EventCard) day.get(i)).getEventCardID() == ID) {
                day.remove(i);
                if (now) i++;
                notifyItemRemoved(i);
            }
        }
    }

    /**
     * Непонятно зачем оно.
     * @param i непонятно что за параметр.
     * @return какойто идентификатор.
     */
    @Override
    public long getItemId(int i) {
        return i;
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