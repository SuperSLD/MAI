package com.raspisanie.mai;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.TimeTableSubAdapter;
import com.raspisanie.mai.Classes.Week;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class TimeTableAppWidget extends AppWidgetProvider {

    private static Week[] weeks;
    private static SharedPreferences mSettings;
    private static int thisWeek;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.time_table_app_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.time_table_app_widget);

            mSettings = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            weeks = gson.fromJson(mSettings.getString("weeks", ""), Week[].class);

            setThisWeek();

            Calendar calendar = Calendar.getInstance();
            int thisDay = -1;
            for (int i = 0; i < weeks[thisWeek].getDaysList().size(); i++) {
                if (Integer.parseInt(weeks[thisWeek].getDaysList().get(i).getDate().substring(0, 2)) ==
                        calendar.get(Calendar.DAY_OF_MONTH))
                    thisDay = i;
            }

            if (thisDay >= 0) {
                views.setTextViewText(R.id.textView1, weeks[thisWeek].getDaysList().get(thisDay).getDate());
                views.setTextViewText(R.id.textView2, weeks[thisWeek].getDaysList().get(thisDay).getName());
            }
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //TODO отловить ситуацию, когда текущая неделя выходит за список существующих.
    private static void setThisWeek() {
        thisWeek = 0;
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            String s = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." +
                    (Calendar.getInstance().get(Calendar.MONTH)+1) + "." + Calendar.getInstance().get(Calendar.YEAR);
            date = ft.parse(s);
        } catch (Exception ex) { ex.printStackTrace(); }
        for (int i = 0; i < weeks.length; i++) {
            Date top = null;
            Date down = null;
            try {
                top = ft.parse(weeks[i].getDate().split(" - ")[1]);
                down = ft.parse(weeks[i].getDate().split(" - ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((!date.before(down) && date.before(top))) thisWeek = i;
            if (date.compareTo(top) == 0 || date.compareTo(down) == 0) thisWeek = i;
        }
    }
}

