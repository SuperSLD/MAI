package pro.midev.juttermap

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import pro.midev.juttermap.common.JTColorData
import pro.midev.juttermap.constraints.JTDefaultConfig
import pro.midev.juttermap.geometry.JTPoint
import pro.midev.juttermap.geometry.JTStartGeometry
import pro.midev.juttermap.gl.JTGlRender
import pro.midev.juttermap.models.map_objects.JTMap
import pro.midev.juttermap.server.callback.JTMapCallback
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Карта строений и внутренних
 * помещений, написанная чтобы упростить
 * всем жизнь.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTMapView : GLSurfaceView {

    private var mMove = false
    private var mLastX = 0f
    private var mLastY = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mZoom = 1f
    private var mRender: JTGlRender? = null

    private var mGestureDetector: GestureDetector? = null
    private var mScaleDetector: ScaleGestureDetector? = null
    private var mColorData: JTColorData? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setEGLContextClientVersion(2)
        mRender = JTGlRender(this@JTMapView)
        mRender?.setColorData(mColorData ?: JTColorData())
        setRenderer(mRender)
    }

    companion object {
        private var mApiKey: String? = null

        /**
         * Установка апи ключа для карты.
         * Его можно получить при создании карты на сайте.
         *
         * @param key уникальный идентификатор в формате UUID.
         */
        fun setApiKey(key: String) {
            mApiKey = key
        }
    }

    private var mDataController: JTDataController? = null
    private var mMap: JTMap? = null
    private var mLoading = false
    private val mMapLoadCallback = object : JTMapCallback {
        override fun onLoad(map: JTMap) {
            val startGeo = JTStartGeometry(map)
            mZoom = startGeo.getStartZoom()
            mCenterX = -startGeo.getStartPosition().latitude.toFloat() * mZoom
            mCenterY = -startGeo.getStartPosition().longitude.toFloat() * mZoom
            this@JTMapView.mMap = map
            mRender?.mapLoaded()
            mLoading = false

            mGestureDetector = GestureDetector(context, MapGestureListener())
            mScaleDetector = ScaleGestureDetector(context, ScaleListener())
        }
        override fun onError(err: String) {
            Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.WARNING, err)
        }
    }

    /**
     * Привязка контролера данных к
     * данному view.
     */
    private fun bindData() {
        if (mApiKey == null) throw IllegalStateException("JutterMapKit api key not installed")
        mDataController = JTDataController.create(mApiKey!!)
    }

    /**
     * Инициализация карты, без загрузки.
     * (вставляем уже загруженную карту, вдруг пригодится)
     *
     * @param map загруженная ранее карта.
     */
    fun init(map: JTMap) {
        bindData()
        this.mMap = map
    }

    /**
     * Загружаем и отображаем карту по ее
     * идентификатору.
     *
     * @param mapId идентификатор карты.
     */
    fun init(mapId: String) {
        bindData()
        mLoading = true
        mDataController?.loadMap(mapId, mMapLoadCallback)
    }

    /**
     * Инициализация без указанной карты.
     * Если карта не указана то будет загружена
     * карта, отмеченная как главная.
     */
    fun init() {
        bindData()
        mLoading = true
        mDataController?.loadMainMap(mMapLoadCallback)
    }

    /**
     * Замена цветов карты.
     * @param cd информация о новых цветах.
     */
    fun setColorData(cd: JTColorData) {
        mColorData = cd
        this.mRender?.setColorData(cd)
    }

    /**
     * Проверка на то, находится камера
     * в движении или нет.
     */
    fun isMove() = mMove

    /**
     * Получение позиции камеры.
     */
    fun getCameraPosition() = JTPoint(
        mCenterX.toDouble(),
        mCenterY.toDouble()
    )

    /**
     * Получение зума камеры.
     */
    fun getZoom() = mZoom

    /**
     * Получение карты.
     */
    fun getMap() = mMap

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (mMap != null) {
            mGestureDetector!!.onTouchEvent(event)
            mScaleDetector!!.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> mMove = true
                MotionEvent.ACTION_UP -> {
                    mMove = false
                    Thread {
                        val k = 20
                        while (mLastX != 0f || mLastY != 0f) {
                            mLastX -= mLastX / k
                            mLastY -= mLastY / k
                            mCenterX -= mLastX
                            mCenterY -= mLastY
                            try {
                                Thread.sleep(10)
                            } catch (e: InterruptedException) {
                            }
                            if (mMove) break
                        }
                        Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(
                            Level.INFO,
                            "CAMERA_POSITION (${mCenterX/mZoom} , ${mCenterY/mZoom})"
                        )
                    }.start()
                }
            }
            return true
        } else return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mMap != null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    inner class MapGestureListener : SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            val wh = mRender?.getWH() ?: Pair(1, 1)
            mCenterX -= distanceX / wh.first.toFloat()
            mCenterY -= distanceY / wh.second.toFloat()
            mLastX = distanceX / wh.first
            mLastY = distanceY / wh.second
            Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(
                Level.INFO,
                "CAMERA_POSITION (${mCenterX/mZoom} , ${mCenterY/mZoom})"
            )
            return false
        }
    }

    /**
     * Масштабирование карты. В обозначенных пределах.
     */
    inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor

            if (true) { //zoom > 0.23605603f && zoom < 1.6001107f) {
                mZoom *= scaleFactor
                mCenterX *= scaleFactor
                mCenterY *= scaleFactor
                Logger.getLogger(JTDefaultConfig.LOGGER_NAME).log(Level.INFO, "SCALE_EVENT zoom = $mZoom")
            }
            return true
        }
    }

    override fun onPause() {

    }
}