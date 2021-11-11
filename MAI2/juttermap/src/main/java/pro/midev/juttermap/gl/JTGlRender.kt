package pro.midev.juttermap.gl

import android.graphics.Color
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import pro.midev.juttermap.JTMapView
import pro.midev.juttermap.R
import pro.midev.juttermap.common.JTColorData
import pro.midev.juttermap.constraints.JTDefaultConfig
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.logging.Level
import java.util.logging.Logger
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Операции с точками и похожие приколы.
 */
class JTGlRender(
    private val mapView: JTMapView
): GLSurfaceView.Renderer  {

    var mColorData = JTColorData()
    private var mProgramId = 0
    private var mVertexData: FloatBuffer? = null
    private var mUColorLocation = 0
    private var mAPositionLocation = 0
    private var mUWindowKLocation = 0
    private var mUCenterXLocation = 0
    private var mUCenterYLocation = 0
    private var mUZoomLocation = 0
    private var mUWindowK = 1f
    private var mW = 0
    private var mH = 0

    private var mVertices: FloatArray = floatArrayOf()

    init {
        prepareData(true)
    }

    /**
     * Обработка вершин сооружений
     * и дорог карты.
     */
    private fun prepareData(isEmpty: Boolean = false) {
        mVertices = if (isEmpty) floatArrayOf() else mapView.getMap()!!.getVertexData()
        mVertexData = ByteBuffer.allocateDirect(mVertices.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexData?.put(mVertices)?.position(0)
    }

    /**
     * Проверяем точки при закгузке карты.
     */
    fun mapLoaded() {
        prepareData()
    }

    /**
     * Получение высоты и ширины.
     */
    fun getWH() = Pair(mW, mH)

    /**
     * Замена дефолтных цветов карты.
     * @param cd параметры цвета карты.
     */
    fun setColorData(cd: JTColorData) {
        this.mColorData = cd
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.INFO, "onSurfaceCreated")
        val vertexShaderId: Int = JTShaderUtils.createShader(mapView.context, GLES20.GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId: Int = JTShaderUtils.createShader(mapView.context, GLES20.GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        mProgramId = JTShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        GLES20.glUseProgram(mProgramId)
        bindData()

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.INFO, "onSurfaceChanged")
        GLES20.glViewport(0, 0, width, height)
        mUWindowK = width / height.toFloat()
        mH = height
        mW = width
        Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.INFO, "uWindowK= $mUWindowK")
    }

    /**
     * Связыкание параметров из шейдеров
     * с параметрами MapView.
     */
    private fun bindData() {
        mUColorLocation = GLES20.glGetUniformLocation(mProgramId, "u_Color")
        mAPositionLocation = GLES20.glGetAttribLocation(mProgramId, "a_Position")
        mUWindowKLocation = GLES20.glGetUniformLocation(mProgramId, "uWindowK")
        mUCenterXLocation = GLES20.glGetUniformLocation(mProgramId, "uCenterX")
        mUCenterYLocation = GLES20.glGetUniformLocation(mProgramId, "uCenterY")
        mUZoomLocation = GLES20.glGetUniformLocation(mProgramId, "uZoom")
        GLES20.glUniform1f(mUWindowKLocation, mUWindowK)
        Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.INFO, "BIND DATA")
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClearColor(
            Color.red(mColorData.backColor) / 255f,
            Color.green(mColorData.backColor) / 255f,
            Color.blue(mColorData.backColor) / 255f, 1f
        )

        GLES20.glUniform1f(mUZoomLocation, mapView.getZoom())
        GLES20.glUniform1f(mUWindowKLocation, mUWindowK)
        GLES20.glUniform1f(mUCenterXLocation, mapView.getCameraPosition().latitude.toFloat())
        GLES20.glUniform1f(mUCenterYLocation, mapView.getCameraPosition().longitude.toFloat())

        mVertexData?.position(0)
        GLES20.glVertexAttribPointer(mAPositionLocation, 2, GLES20.GL_FLOAT, false, 0, mVertexData)
        GLES20.glEnableVertexAttribArray(mAPositionLocation)

        drawTriangle(0, 3, mColorData.buildingColors[1])
        drawTriangle(3, mVertices.size, mColorData.buildingColors[0])
    }

    /**
     * Отрисовка массива треугольников, с определенным цветом.
     * @param from начальный индекс
     * @param to конечный индекс
     * @param color цвет треугольников
     */
    private fun drawTriangle(from: Int, to: Int, color: Int) {
        GLES20.glUniform4f(mUColorLocation, color.red / 255f, color.green / 255f, color.blue / 255f, 1f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, from, to)
    }
}