package com.raspisanie.mai.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;

import com.raspisanie.mai.R;

public class LogoMAIView extends View {
    private int n = 15;

    public LogoMAIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    /**
     * Установка количества линий на рисунке.
     * @param n количество линий.
     */
    private void setLineN(int n) {
        this.n = n;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        // фон
        canvas.drawRect(0,0, w, h, paint);
        for (int i = 0; i < n; i++) {
            paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawRect((w / n) * i + w/100, 0, (w / n)*(i + 1) - (w / 100), h, paint);
            paint.setColor(getResources().getColor(R.color.colorPrimary));
            canvas.drawRect((w / n) * i + w/100, (float) Math.sin(i)*h, (w / n)*(i + 1) - (w / 100), h, paint);
        }

        // надпись
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextSize(h/2);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);
        canvas.drawText("MAИ", w/2 - w/4, h, paint);
    }
}
