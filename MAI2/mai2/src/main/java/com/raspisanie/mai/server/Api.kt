package com.raspisanie.mai.server

import com.raspisanie.mai.models.DataWrapper
import com.raspisanie.mai.models.server.GroupResponse
import com.raspisanie.mai.models.server.WeekResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {
    @GET("groups/search/{name}")
    fun getGroupList(@Path("name") name: String) : Single<DataWrapper<MutableList<GroupResponse>>>

    @GET("schedule/all/{id}")
    fun getSchedule(@Path("id") id: String) : Single<DataWrapper<MutableList<WeekResponse>>>
}