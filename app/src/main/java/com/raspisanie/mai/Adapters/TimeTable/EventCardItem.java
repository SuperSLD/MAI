package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.R;

import jp.wasabeef.blurry.Blurry;

/**
 * ViewHolder карточки с мероприятием.
 */
public class EventCardItem extends TimeTableViewHolder {

    private TextView textViewName;
    private TextView textViewDate;
    private Button closeButton;
    private Button infoButton;

    private Context context;
    private ImageView imageView;

    private TimeTableAdapter mainAdapter;
    private MainActivity activity;

    public EventCardItem(@NonNull View itemView, Context context, TimeTableAdapter adapter, MainActivity activity) {
        super(itemView);
        this.textViewName = itemView.findViewById(R.id.name);
        this.textViewDate = itemView.findViewById(R.id.date);
        this.closeButton = itemView.findViewById(R.id.buttonClose);
        this.infoButton = itemView.findViewById(R.id.buttonInfo);

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
        EventCard eventCard = (EventCard) obj;
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
        closeButton.setOnClickListener(
                v -> mainAdapter.deleteEventCardByID(eventCard.getEventCardID()));
        infoButton.setOnClickListener(
                v -> activity.openEventInfo(eventCard));
    }
}
