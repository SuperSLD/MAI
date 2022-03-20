package com.raspisanie.mai.data.net.retrofit

import com.raspisanie.mai.data.net.models.*
import com.raspisanie.mai.data.net.DataWrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*


interface Api {
    @GET("groups/search/{name}")
    suspend fun getGroupList(@Path("name") name: String) : DataWrapper<MutableList<GroupResponse>>

    @GET("schedule/all/{id}")
    suspend fun getSchedule(@Path("id") id: String) : DataWrapper<MutableList<WeekResponse>>

    @POST("feedback/send")
    suspend fun sendFeedback(@Body body: FeedbackBody) : DataWrapper<Any?>

    @GET("advertisements/get")
    suspend fun getAdvPage(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ) : DataWrapper<MutableList<AdvResponse>>

    @GET("canteens/all")
    suspend fun getCanteens() : DataWrapper<MutableList<CanteenResponse>>

    @GET("studorg/all")
    suspend fun getStudents() : DataWrapper<MutableList<StudentOrganizationResponse>>

    @GET("developers/all")
    suspend fun getDevList() : DataWrapper<MutableList<DevResponse>>

    @GET("sport/all")
    suspend fun getSport() : DataWrapper<MutableList<SportResponse>>

    @GET("library/all")
    suspend fun getLibrary() : DataWrapper<MutableList<LibraryResponse>>

    @GET("creative/all")
    suspend fun getCreative() : DataWrapper<MutableList<CreativeResponse>>

    @GET("notification/all/{date}")
    suspend fun getNotifications(@Path("date") date: String) : DataWrapper<NotificationsResponse>

    @GET("news/get")
    suspend fun getNews(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ) : DataWrapper<MutableList<NewsResponse>>

    @GET("news/like/{id}")
    suspend fun likeNews(@Path("id") id: String) : DataWrapper<Boolean?>

    @GET("advertisements/like/{id}")
    suspend fun likeAdv(@Path("id") id: String) : DataWrapper<Boolean?>

    @GET("schedule/searchLector/{name}")
    suspend fun getSearchLector(@Path("name") name: String) : DataWrapper<MutableList<TeacherResponse>>

    @GET("schedule/lector/{id}")
    suspend fun getLectorSchedule(@Path("id") id: String) : DataWrapper<MutableList<WeekResponse>>

    @POST("advertisements/add")
    suspend fun createAdv(@Body body: AdvCreateBody) : DataWrapper<Any?>
}