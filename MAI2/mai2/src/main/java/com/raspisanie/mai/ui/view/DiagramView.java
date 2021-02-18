package com.raspisanie.mai.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.raspisanie.mai.R;

import java.util.ArrayList;

public class DiagramView extends View {
    private String centerText = "";
    private ArrayList<Float> data = null;
    private ArrayList<Integer> colors = null;
    private float dataSum = 0;
    private String centerSubText = "";

    private int colorText = 0;
    private int colorSubText = 0;
    float lastAngle = -90;

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) { }

    /**
     * Установка текста в центре диаграммы
     * @param text текст
     */
    public void setCenterText(String text) {
        this.centerText = text;
    }

    /**
     * Установка цветов надписей.
     * @param colorText цвет 1
     * @param colorSubText цвет 2
     */
    public void setColorText(int colorText, int colorSubText) {
        this.colorSubText = colorSubText;
        this.colorText = colorText;
    }

    /**
     * Установка текста под основной надписью.
     * @param text текст.
     */
    public void setCenterSubText(String text) {
        this.centerSubText = text;
    }

    /**
     * Установка данных для отобрадения и цветов которыми данные будут отображаться.
     * @param data данные
     */
    public void setData(ArrayList<Float> data, ArrayList<Integer> colors) {
        if (data.size()> colors.size()) throw new IndexOutOfBoundsException("data.length > colors.length");
        this.colors = colors;
        this.data = data;
        for (float s : data) {
            this.dataSum += s;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // диаграмма
        if (data == null) {
            paint.setColor(getResources().getColor(R.color.colorPrimary));
            canvas.drawCircle(w / 2, h / 2, h / 2, paint);
        } else {
            //float lastAngle = -90;
            for (int i = 0; i < data.size(); i++) {
                float angle = ((data.get(i)*360)/dataSum);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    paint.setColor(colors.get(i));
                    canvas.drawArc(w/2 - h/2, 0, w/2 + h/2, h
                            , lastAngle, angle, true, paint);
                }
                lastAngle += angle;
            }
            for (int i = 0; i < data.size(); i++) {
                float angle = ((data.get(i)*360)/dataSum);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    paint.setColor(colors.get(i));
                    canvas.drawCircle((float) (Math.cos(Math.toRadians(lastAngle + angle))*(h/2 - h/20)) + w/2,
                            (float) (Math.sin(Math.toRadians(lastAngle + angle))*(h/2 - h/20)) + h/2,
                            h / 20, paint);
                }
                lastAngle += angle;
            }
        }
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorBackground));
        canvas.drawCircle(w / 2, h / 2, h / 2 - h / 10, paint);

        // надпись в центре
        paint.setColor(colorText);
        paint.setTextSize(h/10);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);
        canvas.drawText(centerText, w/2, h/2, paint);
        paint.setTextSize(h/15);
        paint.setFakeBoldText(false);
        paint.setColor(colorSubText);
        canvas.drawText(centerSubText, w/2, h/2 + h/10, paint);
    }
}
