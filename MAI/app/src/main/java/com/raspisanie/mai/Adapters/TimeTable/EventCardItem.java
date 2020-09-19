package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.R;

import jp.wasabeef.blurry.Blurry;

import static android.support.v7.widget.helper.ItemTouchHelper.Callback.getDefaultUIUtil;

/**
 * ViewHolder карточки с мероприятием.
 */
public class EventCardItem extends TimeTableViewHolder {

    private TextView textViewName;
    private TextView textViewDate;
    private Button infoButton;

    private Context context;
    private ImageView imageView;

    private TimeTableAdapter mainAdapter;
    private MainActivity activity;

    private LinearLayout background;
    private LinearLayout foreground;
    private LinearLayout nonSwipeForeground;
    private EventCard eventCard;

    public EventCardItem(@NonNull View itemView, Context context, TimeTableAdapter adapter, MainActivity activity) {
        super(itemView);
        this.textViewName = itemView.findViewById(R.id.name);
        this.textViewDate = itemView.findViewById(R.id.date);
        this.infoButton = itemView.findViewById(R.id.buttonInfo);

        this.foreground = itemView.findViewById(R.id.foreground);
        this.background = itemView.findViewById(R.id.background);

        this.context = context;
        this.imageView = itemView.findViewById(R.id.image);
        this.mainAdapter = adapter;
        this.activity = activity;
    }

    /**
     * Передача параметров в view элементы.
     * @param obj день недели.
     */
    @Override
    public void bind(Object obj) {
        foreground.setVisibility(View.VISIBLE);
        EventCard eventCard = (EventCard) obj;
        this.eventCard = eventCard;
        imageView.post(() -> {
            if (eventCard.getBitmap() != null) {
                int delta = 50;
                Blurry.with(context)
                        .radius(25)
                        //.color(Color.argb(170, 101, 168, 198))
                        .color(Color.argb(170, 50 - delta, 108 - delta, 198 - delta))
                        .async()
                        .animate(3000)
                        .from(eventCard.getBitmap())
                        .into(imageView);
            }
        });

        textViewName.setText(eventCard.getName());
        textViewDate.setText(eventCard.getDate());
        infoButton.setOnClickListener(
                v -> activity.openEventInfo(eventCard));
    }

    /**
     * Получение передней части карточки для свайпа.
     * @return
     */
    public LinearLayout getForeground() {
        return foreground;
    }

    /**
     * Получение CardI.
     * @return
     */
    public void deleteCard() {
        mainAdapter.deleteEventCardByID(this.eventCard.getEventCardID());
        this.eventCard.setDeleteState(true);
    }

    /**
     * Анимация при свайпе.
     * @param c
     * @param recyclerView
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    public void swipe(Canvas c, RecyclerView recyclerView, float dX, float dY,
                       int actionState, boolean isCurrentlyActive) {

        if (dX < 0) {
            background.setVisibility(View.VISIBLE);
            getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY,
                    actionState, isCurrentlyActive);
        }
        if (dX == 0) {
            background.setVisibility(View.INVISIBLE);
        }
        if (dX == -foreground.getWidth()) {
            foreground.setVisibility(View.INVISIBLE);
            getDefaultUIUtil().onDraw(c, recyclerView, foreground, 0, dY,
                    actionState, isCurrentlyActive);
        }
    }
}
