package pro.midev.juttermap.server

import pro.midev.juttermap.models.JTDataWrapper
import pro.midev.juttermap.models.map_objects.JTMap
import pro.midev.juttermap.models.server.JTMapData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Интерфейс с методами для
 * обращения к серверу.
 */
interface JTApi {
    @GET("map/list")
    suspend fun getMapList(): JTDataWrapper<MutableList<JTMapData>>

    @GET("map/data/{id}")
    suspend fun getMap(@Path("id") id: String): JTDataWrapper<JTMap>
}