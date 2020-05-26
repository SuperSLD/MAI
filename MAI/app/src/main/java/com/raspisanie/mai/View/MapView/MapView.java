package com.raspisanie.mai.View.MapView;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.raspisanie.mai.R;
import com.raspisanie.mai.View.MapView.Classes.Map;
import com.raspisanie.mai.View.MapView.Classes.Road;
import com.raspisanie.mai.View.MapView.Classes.Structure;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.glActiveTexture;
import static android.opengl.GLES10.glBindTexture;
import static android.opengl.GLES11.glTexCoordPointer;
import static android.opengl.GLES11.glVertexPointer;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * @author Леонид Соляной (solyanoy.leonid@gmail.com)
 *
 * Отображение карты кампуса.
 */
public class MapView extends GLSurfaceView {
    private Map map;
    private Context context;

    private boolean move = false;
    private float lastX;
    private float lastY;
    private float centerX = 0;
    private float centerY = 0;
    private float zoom = 1f;

    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector scaleDetector;

    private GLTextureManager glTextureManager;

    private int w;
    private int h;

    float[] vertices;

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        String data = null;
        this.context = context;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream input;
            input = assetManager.open("campusMap.campus");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            data = new String(buffer);
        } catch (Exception ex) {}
        map = new Map(data);
        scrollBy(getWidth()/2, getHeight()/2);

        glTextureManager = new GLTextureManager(context);

        vertices = map.getVertices();
        map.createTextTextures(glTextureManager);
        setEGLContextClientVersion(2);
        setRenderer(new OpenGLRenderer(context));

        gestureDetector = new GestureDetector(context, new MapGestureListener());
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * @author Леонид Соляной (solyanoy.leonid@gmail.com)
     *
     * Отрисовка карты по составленному списку треугольников.
     */
    public class OpenGLRenderer implements GLSurfaceView.Renderer {
        private Context context;
        private int programId;
        private FloatBuffer vertexData;
        private int uColorLocation;
        private int aPositionLocation;
        private int uWindowKLocation;
        private int uCenterXLocation;
        private int uCenterYLocation;
        private int uZoomLocation;

        private float uWindowK = 1;

        public OpenGLRenderer(Context context) {
            this.context = context;
            prepareData();
        }

        @Override
        public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
            Logger.getLogger("mapview").log(Level.INFO, "MapView onSurfaceCreated");
            int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
            int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
            programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
            glUseProgram(programId);
            bindData();
        }

        @Override
        public void onSurfaceChanged(GL10 arg0, int width, int height) {
            Logger.getLogger("mapview").log(Level.INFO, "MapView onSurfaceChanged");
            glViewport(0, 0, width, height);
            uWindowK = width/(float)height;
            h = height;
            w = width;
            Logger.getLogger("mapview").log(Level.INFO, "uWindowK= " + uWindowK);
        }

        private void prepareData() {
            vertexData = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexData.put(vertices);
        }

        private void bindData() {
            uColorLocation    = glGetUniformLocation(programId, "u_Color");
            aPositionLocation = glGetAttribLocation(programId, "a_Position");
            uWindowKLocation  = glGetUniformLocation(programId, "uWindowK");
            uCenterXLocation  = glGetUniformLocation(programId, "uCenterX");
            uCenterYLocation  = glGetUniformLocation(programId, "uCenterY");
            uZoomLocation = glGetUniformLocation(programId, "uZoom");

            glUniform1f(uWindowKLocation, uWindowK);
            vertexData.position(0);
            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
            glEnableVertexAttribArray(aPositionLocation);
        }

        private final float[] VERTEX_COORDINATES = new float[] {
                -1.0f, +1.0f, 0.0f,
                +1.0f, +1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                +1.0f, -1.0f, 0.0f
        };

        private final float[] TEXTURE_COORDINATES = new float[] {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        };

        @Override
        public void onDrawFrame(GL10 arg0) {
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(240/255f,240/255f,240/255f,1f);

            glUniform1f(uZoomLocation,    zoom);
            glUniform1f(uWindowKLocation, uWindowK);
            glUniform1f(uCenterXLocation, centerX);
            glUniform1f(uCenterYLocation, centerY);

            int startIndex = 0;
            glUniform4f(uColorLocation, 197/255f, 239/255f, 199/255f, 1f);
            glDrawArrays(GL_TRIANGLES, 0, map.getGrassCount());
            startIndex += map.getGrassCount();

            for (int i = 0; i < 4; i++) {
                glLineWidth(Road.SIZE[i] * 0.5f * 5 * zoom);
                glUniform4f(uColorLocation, Road.COLORS[i][0], Road.COLORS[i][1], Road.COLORS[i][2], 1f);
                glDrawArrays(GL_LINES, startIndex, map.getTypeRoadCount()[i]);
                startIndex += map.getTypeRoadCount()[i];
            }

            for (int i = 0; i < 4; i++) {
                glUniform4f(uColorLocation, Structure.COLORS[i][0], Structure.COLORS[i][1], Structure.COLORS[i][2], 1f);
                glDrawArrays(GL_TRIANGLES, startIndex, map.getTypeStructureCount()[i]);
                startIndex += map.getTypeStructureCount()[i];
            }

            glActiveTexture(GL10.GL_TEXTURE0);
            glBindTexture(GL10.GL_TEXTURE_2D, glTextureManager.getTextureId("0000"));

            /*
            glVertexPointer(3, GL10.GL_FLOAT, 0, VERTEX_BUFFER);
            glTexCoordPointer(2, GL10.GL_FLOAT, 0, TEXCOORD_BUFFER);
            glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
             */
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                move = true;
                break;
            case MotionEvent.ACTION_UP:
                move = false;
                new Thread(() -> {
                    int k = 15;
                    while (lastX != 0 || lastY != 0) {
                        lastX -= lastX/k;
                        lastY -= lastY/k;

                        centerX -= lastX;
                        centerY -= lastY;

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {}
                        if (move) break;
                    }
                }).start();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private class MapGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            Logger.getLogger("mapview").log(Level.INFO, "scroll x= " + distanceX + " y= " + distanceY);
            centerX -= distanceX/(float) w;
            centerY -= distanceY/(float) h;

            lastX = distanceX/w;
            lastY = distanceY/h;
            return false;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();

            zoom    *= scaleFactor;
            centerX *= scaleFactor;
            centerY *= scaleFactor;
            Logger.getLogger("mapview").log(Level.INFO, "SCALE_EVENT");
            return true;
        }
    }
}
