package com.raspisanie.mai.View;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.raspisanie.mai.Classes.MapClasses.GrassZone;
import com.raspisanie.mai.Classes.MapClasses.Map;
import com.raspisanie.mai.Classes.MapClasses.Road;
import com.raspisanie.mai.Classes.MapClasses.Structure;
import com.raspisanie.mai.R;

import java.io.InputStream;

public class MapView extends View {
    private Map map;

    private float cameraX = 0;
    private float cameraY = 0;

    private final GestureDetector gestureDetector;
    private ScaleGestureDetector mScaleDetector;


    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        String data = null;
        gestureDetector = new GestureDetector(context, new GestureListener());
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        try {
            AssetManager assetManager = context.getAssets();
            InputStream input;
            input = assetManager.open("campusMap.campus");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            data = new String(buffer);
        } catch (Exception ex) {}
        map = new Map(data);
        scrollBy(getWidth()/2, getHeight()/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        canvas.drawColor(Color.parseColor("#F0F0F0"));


        for (GrassZone grassZone : map.getGrassZones()) {
            paint.setColor(Color.parseColor(GrassZone.COLOR));
            paint.setStyle(Paint.Style.FILL);

            Path struct = new Path();
            for (int i = 0; i < grassZone.getX().size(); i++) {
                struct.lineTo(grassZone.getX().get(i), grassZone.getY().get(i));
            }
            struct.lineTo(grassZone.getX().get(0), grassZone.getY().get(0));
            canvas.drawPath(struct, paint);
        }

        for (Road road : map.getRoads()) {
            paint.setColor(Color.parseColor(road.getColor()));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(road.getSize());
            if (road.getDASH()) {
                paint.setPathEffect(new DashPathEffect(new float[]{4, 2}, 0));
            } else {
                paint.setPathEffect(new DashPathEffect(new float[]{1, 0}, 0));
            }

            Path struct = new Path();
            struct.moveTo(road.getX().get(0), road.getY().get(0));
            for (int i = 0; i < road.getX().size(); i++) {
                struct.lineTo(road.getX().get(i), road.getY().get(i));
            }
            canvas.drawPath(struct, paint);
        }

        for (Structure structure : map.getStructures()) {
            paint.setColor(Color.parseColor(structure.getColor()));
            paint.setStyle(Paint.Style.FILL);

            Path struct = new Path();
            struct.moveTo(structure.getX().get(0) + cameraX,
                    structure.getY().get(0) + cameraY);
            for (int i = 0; i < structure.getX().size(); i++) {
                struct.lineTo(structure.getX().get(i) + cameraX,
                        structure.getY().get(i) + cameraY);
            }
            struct.lineTo(structure.getX().get(0) + cameraX,
                    structure.getY().get(0) + cameraY);
            canvas.drawPath(struct, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollBy((int)distanceX, (int)distanceY);
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            return true;
        }
    }
}
