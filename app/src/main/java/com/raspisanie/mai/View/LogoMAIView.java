package com.raspisanie.mai.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.raspisanie.mai.R;

public class LogoMAIView extends View {
    public LogoMAIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));

        // фон
        canvas.drawRect(0,0, w, h, paint);



        // надпись
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextSize(h/2);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);
        canvas.drawText("MAИ", w/2 - w/4, h, paint);
    }
}
