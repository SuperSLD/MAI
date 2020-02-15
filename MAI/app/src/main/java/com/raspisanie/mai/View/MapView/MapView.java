package com.raspisanie.mai.View.MapView;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.raspisanie.mai.R;
import com.raspisanie.mai.View.MapView.Classes.Map;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLU.gluLookAt;

public class MapView extends GLSurfaceView {
    private Map map;
    private Context context;

    private final float[] viewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];


    /** Используется для передачи в матрицу преобразований. */
    private int mMVPMatrixHandle;

    /** Используется для передачи информации о положении модели. */
    private int mPositionHandle;

    /** Используется для передачи информации о цвете модели. */
    private int mColorHandle;

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

            // byte buffer into a string
            data = new String(buffer);
        } catch (Exception ex) {}
        map = new Map(data);
        scrollBy(getWidth()/2, getHeight()/2);

        vertices = map.getVertices();
        setEGLContextClientVersion(2);
        setRenderer(new OpenGLRenderer(context));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.getLogger("mapview").log(Level.INFO, "MapView x: " + event.getX() + " / y: " + event.getY());
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    public class OpenGLRenderer implements GLSurfaceView.Renderer {
        private Context context;
        private int programId;
        private FloatBuffer vertexData;
        private int uColorLocation;
        private int aPositionLocation;

        public OpenGLRenderer(Context context) {
            this.context = context;
            prepareData();
        }

        @Override
        public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
            Logger.getLogger("mapview").log(Level.INFO, "MapView onSurfaceCreated");
            glClearColor(0f, 0f, 0f, 1f);
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
        }

        private void prepareData() {
            vertexData = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexData.put(vertices);
        }

        private void bindData() {
            uColorLocation = glGetUniformLocation(programId, "u_Color");
            glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 0.2f);
            aPositionLocation = glGetAttribLocation(programId, "a_Position");
            vertexData.position(0);
            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
            glEnableVertexAttribArray(aPositionLocation);
        }

        @Override
        public void onDrawFrame(GL10 arg0) {
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(240/255f,240/255f,240/255f,1f);

            glDrawArrays(GL_TRIANGLES, 0, vertices.length/2);
        }
    }
}
