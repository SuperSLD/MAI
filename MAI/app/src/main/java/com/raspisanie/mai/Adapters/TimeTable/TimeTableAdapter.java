package com.raspisanie.mai.Adapters.TimeTable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.Classes.TimeTable.Day;
import com.raspisanie.mai.Classes.TimeTable.EventCardListManager;

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
    private ArrayList<Object> objects;
    private int daySum;

    private ItemTouchHelper itemTouchHelper;
    private RecyclerView recyclerView;

    /**
     * Конструктор адаптера.
     *
     * @param day1 список элементов для отображения.
     */
    public TimeTableAdapter(ArrayList day1, int week, boolean now) {
        this.now = now;
        this.notFirstDay = false;
        this.showAllDays = false;

        ArrayList<Day> day = (ArrayList<Day>) day1;
        this.startDayList = day;
        this.objects =  new ArrayList<>();

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
                    this.objects.add(day.get(i));
                } else {
                    notFirstDay = true;
                }
            } else {
                // если неделя не равна текущей то показываем все дни
                this.objects.add(day.get(i));
            }
        }
        daySum = day.size() - this.objects.size() - 1;
        EventCardListManager.getInstance().insertEventCardsInList(this.objects, week);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new SwipeListener(0, ItemTouchHelper.LEFT);
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
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
                if (objects.get(position) instanceof Day) {
                    return 0;
                } else if (objects.get(position) instanceof EventCard) {
                    return 3;
                } else {
                    return -1;
                }
            }
        } else {
            if (objects.get(position) instanceof Day) {
                return 0;
            } else if (objects.get(position) instanceof EventCard) {
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
            if (i != 0) item.bind(objects.get(i - 1));
        } else item.bind(objects.get(i));
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
        Logger.getLogger("mailog").log(Level.INFO, "objects list [size = "+ objects.size()+"]");
    }

    /**
     * Вставка объекта с анимацией.
     * @param obj объект.
     * @param i позиция для нового объекта.
     */
    public void insertItem(Object obj, int i) {
        if (i < objects.size()) {
            objects.add(i, obj);
        } else {
            objects.add(obj);
            i = objects.size() - 1;
        }
        notifyItemInserted(i);
    }

    /**
     * Удаление объекта с анимацией.
     * @param i позиция удаляемного элемента.
     */
    public void deleteItem(int i) {
        objects.remove(i);
        notifyItemRemoved(i);
    }

    /**
     * Удаление EventCard с анимацией.
     * @param ID идентификатор элемента.
     */
    public void deleteEventCardByID(int ID) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof EventCard && ((EventCard) objects.get(i)).getEventCardID() == ID) {
                Logger.getLogger("mailog").log(Level.INFO, "search event card by id ["+((EventCard) objects.get(i)).getEventCardID()
                        +" / "+ ID + " / size = "+ objects.size()+"]");
                objects.remove(i);
                if (now) i++;
                notifyItemRemoved(i);
                Logger.getLogger("mailog").log(Level.INFO, "DELETE EVENT CARD = " + ID + " / objects size after remove =" + objects.size());
                return;
            }
        }
        Logger.getLogger("mailog").log(Level.INFO, "event card not found [ID = "+ID+" size = "+ objects.size()+"]");
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
                return objects.size() + 1;
            } else {
                return objects.size();
            }
        } else return objects.size();
    }
}