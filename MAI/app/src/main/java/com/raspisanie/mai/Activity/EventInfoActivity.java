package com.raspisanie.mai.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTable.EventCard;
import com.raspisanie.mai.InformationConnection.InformationConnection;
import com.raspisanie.mai.R;

import jp.wasabeef.blurry.Blurry;

public class EventInfoActivity extends AppCompatActivity {

    private EventCard eventCard;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        eventCard = (EventCard) Parametrs.getParam("eventInfoParam");

        InformationConnection.sendInfoActivity(this.getClass(), "onCreate()");

        ImageView imageView = findViewById(R.id.background);
        if (eventCard.getBitmap() != null) {
            int delta = 50;
            Blurry.with(this)
                    .radius(25)
                    //.color(Color.argb(170, 101, 168, 198))
                    .color(Color.argb(170, 50 - delta, 108 - delta, 198 - delta))
                    .async()
                    .animate(3000)
                    .from(eventCard.getBitmap())
                    .into(imageView);
        }

        findViewById(R.id.back).setOnClickListener(v -> {
            onBackPressed();
        });
        findViewById(R.id.backDown).setOnClickListener(v -> {
            onBackPressed();
        });
        ((TextView)findViewById(R.id.name)).setText(eventCard.getName());
        ((TextView)findViewById(R.id.date)).setText(eventCard.getDate());
        ((TextView)findViewById(R.id.information)).setText(eventCard.getInfo());

    }
}