package com.raspisanie.mai.Adapters.TimeTable;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raspisanie.mai.Classes.EventCard;
import com.raspisanie.mai.R;

import jp.wasabeef.blurry.Blurry;

/**
 * ViewHolder карточки с мероприятием.
 */
public class EventCardItem extends TimeTableViewHolder {

    //TODO временно
    TextView textView;

    private Context context;
    private ImageView imageView;

    public EventCardItem(@NonNull View itemView, Context context) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.hello);
        this.context = context;
        this.imageView = itemView.findViewById(R.id.image);
    }

    /**
     * Передача параметров в view элементы.
     * @param obj день недели.
     */
    @Override
    public void bind(Object obj) {
        EventCard eventCard = (EventCard) obj;

        /*
        Blurry.with(context)
                .radius(10)
                .color(context.getResources().getColor(R.color.colorPrimary))
                .onto(imageView);*/

        textView.setText(eventCard.getName());
    }
}
