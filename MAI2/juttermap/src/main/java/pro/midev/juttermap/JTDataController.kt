package pro.midev.juttermap

import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import pro.midev.juttermap.constraints.JTDefaultConfig
import pro.midev.juttermap.models.map_objects.JTMap
import pro.midev.juttermap.models.server.JTMapData
import pro.midev.juttermap.server.JTApi
import pro.midev.juttermap.server.JTApiService
import pro.midev.juttermap.server.JTMapDeserializer
import pro.midev.juttermap.server.callback.JTLoadMapsCallback
import pro.midev.juttermap.server.JTTokenInterceptor
import pro.midev.juttermap.server.callback.JTMapCallback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * Прослойка для связи данных с сервером.
 * Отвечает за получение объектов и их кэширование,
 * а также за оптимизацию при работе с несколькими [JTMapView]
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTDataController private constructor(key: String) {
    companion object {
        private var mInstance: JTDataController? = null

        /**
         * Создание контроллера или получение
         * готового экземпляра при повторной инициализации.
         *
         * @param key ключ для доступа к картам.
         */
        fun create(key: String) = if (mInstance != null) {
            mInstance
        } else {
            mInstance = JTDataController(key)
            mInstance
        }
    }

    /**
     * Создание объекта сервиса ретрофита.
     */
    private val mApiKey: String = key
    private val mClient = OkHttpClient.Builder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(JTTokenInterceptor(mApiKey))
        .build()
    private val mGsonBuilder = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .setLenient()
        .registerTypeAdapter(JTMap::class.java, JTMapDeserializer())
        .create()
    private val mRetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(mGsonBuilder))
        .client(mClient)
    private val mRetrofit = mRetrofitBuilder.baseUrl(JTDefaultConfig.BASE_URL)
        .build()
    private val apiService = JTApiService(mRetrofit.create(JTApi::class.java))

    /**
     * Сохранение полученных результатов.
     */
    private val mMapDataList = mutableListOf<JTMapData>()
    private val mMaps = hashMapOf<String, JTMap>()

    /**
     * Загрузка списка карт, прикрепленных
     * к данному ключу.
     *
     * @param callback колбэк для получения ответа.
     */
    fun loadMaps(callback: JTLoadMapsCallback?) {
        if (mMapDataList.isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getMapList()
                    withContext(Dispatchers.Main) {
                        if (response.success) {
                            mMapDataList.clear()
                            mMapDataList.addAll(response.data!!)
                            callback?.onLoad(response.data!!)
                        } else {
                            callback?.onError(response.message!!)
                        }
                    }
                } catch (ex: Exception) { callback?.onError(ex.message.toString()) }
            }
        } else {
            callback?.onLoad(mMapDataList)
        }
    }

    /**
     * Загрузка данных одной карты по
     * идентификатору.
     *
     * @param id идентификатор карты.
     * @param callback колбэк для получения ответа.
     */
    fun loadMap(id: String, callback: JTMapCallback?) {
        if (mMaps[id] == null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getMap(id)
                    withContext(Dispatchers.Main) {
                        if (response.success) {
                            mMaps[id] = response.data!!
                            callback?.onLoad(response.data!!)
                        } else {
                            callback?.onError(response.message!!)
                        }
                    }
                } catch (ex: Exception) { callback?.onError(ex.message.toString()) }
            }
        } else {
            callback?.onLoad(mMaps[id]!!)
        }
    }

    /**
     * Получение главной карты,
     * прикрепленной к апи ключу.
     */
    fun loadMainMap(callback: JTMapCallback?) {
        loadMaps(object : JTLoadMapsCallback {
            override fun onLoad(maps: MutableList<JTMapData>) {
                val mainMap = maps.find { it.main }
                if (mainMap != null) {
                    loadMap(mainMap.id, callback)
                } else {
                    callback?.onError("main map not found, check settings in workspace")
                }
            }
            override fun onError(err: String) {
                callback?.onError(err)
            }
        })
    }
}