package com.raspisanie.mai.server

import com.raspisanie.mai.models.DataWrapper
import com.raspisanie.mai.models.server.FeedbackBody
import com.raspisanie.mai.models.server.GroupResponse
import com.raspisanie.mai.models.server.WeekResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Api {
    @GET("groups/search/{name}")
    fun getGroupList(@Path("name") name: String) : Single<DataWrapper<MutableList<GroupResponse>>>

    @GET("schedule/all/{id}")
    fun getSchedule(@Path("id") id: String) : Single<DataWrapper<MutableList<WeekResponse>>>

    @POST("feedback/send")
    fun sendFeedback(@Body body: FeedbackBody) : Single<DataWrapper<Any?>>
}