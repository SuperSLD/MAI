package com.raspisanie.mai.server

import com.raspisanie.mai.models.DataWrapper
import com.raspisanie.mai.models.server.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*


interface Api {
    @GET("groups/search/{name}")
    fun getGroupList(@Path("name") name: String) : Single<DataWrapper<MutableList<GroupResponse>>>

    @GET("schedule/all/{id}")
    fun getSchedule(@Path("id") id: String) : Single<DataWrapper<MutableList<WeekResponse>>>

    @POST("feedback/send")
    fun sendFeedback(@Body body: FeedbackBody) : Single<DataWrapper<Any?>>

    @GET("advertisements/get")
    fun getAdvPage(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ) : Single<DataWrapper<MutableList<AdvResponse>>>

    @GET("canteens/all")
    fun getCanteens() : Single<DataWrapper<MutableList<CanteenResponse>>>

    @GET("studorg/all")
    fun getStudents() : Single<DataWrapper<MutableList<StudentOrganizationResponse>>>

    @GET("developers/all")
    fun getDevList() : Single<DataWrapper<MutableList<DevResponse>>>

    @GET("sport/all")
    fun getSport() : Single<DataWrapper<MutableList<SportResponse>>>

    @GET("library/all")
    fun getLibrary() : Single<DataWrapper<MutableList<LibraryResponse>>>

    @GET("creative/all")
    fun getCreative() : Single<DataWrapper<MutableList<CreativeResponse>>>

    @GET("notification/all/{date}")
    fun getNotifications(@Path("date") date: String) : Single<DataWrapper<NotificationsResponse>>

    @GET("news/get")
    fun getNews(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ) : Single<DataWrapper<MutableList<NewsResponse>>>

    @GET("news/like/{id}")
    fun likeNews(@Path("id") id: String) : Single<DataWrapper<Boolean?>>

    @GET("advertisements/like/{id}")
    fun likeAdv(@Path("id") id: String) : Single<DataWrapper<Boolean?>>

    @GET("schedule/searchLector/{name}")
    fun getSearchLector(@Path("name") name: String) : Single<DataWrapper<MutableList<TeacherResponse>>>

    @GET("schedule/lector/{id}")
    fun getLectorSchedule(@Path("id") id: String) : Single<DataWrapper<MutableList<WeekResponse>>>

    @POST("advertisements/add")
    fun createAdv(@Body body: AdvCreateBody) : Single<DataWrapper<Any?>>
}