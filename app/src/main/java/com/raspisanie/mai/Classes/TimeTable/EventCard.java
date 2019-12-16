package com.raspisanie.mai.Classes.TimeTable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Карточка события
 */
public class EventCard {

    private String name;
    private String date;
    private Bitmap bitmap;

    private static int eventCardCount = 0;
    private int eventCardID;

    public EventCard(String name, String date, Bitmap bitmap) {
        this.name = name;
        this.date = date;
        this.bitmap = bitmap;
        Logger.getLogger("mailog").log(Level.INFO, "create EventCard");
        eventCardCount++;
        this.eventCardID = eventCardCount;
    }

    /**
     * Получение названия мероприятия.
     *
     * @return название.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Получение даты мероприятия.
     *
     * @return дата.
     */
    public String getDate() {
        return date;
    }

    /**
     * Вернуть идентификатро карточки.
     * @return идентификатор.
     */
    public int getEventCardID() {
        return eventCardID;
    }

    /**
     * Получение картинки мероприятия.
     *
     * @return картинка.
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Создание Bitmap с нужным размером для экономии памяти.
     * (копипвста с StackOverflow с внесенными изменениями)
     *
     * @return bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Рассчет требуемого размера bitmap.
     * (копипвста с StackOverflow)
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}