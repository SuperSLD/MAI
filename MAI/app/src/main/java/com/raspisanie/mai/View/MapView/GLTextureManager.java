package com.raspisanie.mai.View.MapView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLUtils;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.*;

/**
 * @author Леонид Соляной (solyanoy.leonid@gmail.com)
 *
 * Класс для хранения информации о текстурах.
 * Logger "gl_texture_manager"
 */
public class GLTextureManager {
    private Context context;
    private HashMap<String, Integer> idList;
    private int textureSlot;

    public GLTextureManager(Context context) {
        super();
        this.idList = new HashMap<>();
        this.context = context;
        this.textureSlot = 0;
    }

    /**
     * @author Леонид Соляной (solyanoy.leonid@gmail.com)
     *
     * Создание текстуры текста.
     * @param text
     */
    public void createTextTexture(String text) {
        // Create an empty, mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
        // get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);

        // Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(32);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        // draw the text centered
        canvas.drawText(text, 16,112, textPaint);

        //Generate one texture pointer...
        final int[] textureIds = new int[1];
        glActiveTexture(textureSlot);
        glGenTextures(1, textureIds, 0);
        //...and bind it to our array
        glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);

        //Create Nearest Filtered Texture
        glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        //Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        //Clean up
        bitmap.recycle();
        //idList.put(text, textureIds[0]);
        idList.put("0000", textureSlot);
        Logger.getLogger("gl_texture_manager").log(Level.INFO, "create texture [name:"+
                text+" / textureIds:"+textureSlot+"]");
        textureSlot++;
    }

    /**
     * Получение сохраненной GL_TEXTURE по имени.
     * @param name название текстуры.
     * @return id текстуры
     */
    public int getTextureId(String name) {
        return this.idList.get(name);
    }
}