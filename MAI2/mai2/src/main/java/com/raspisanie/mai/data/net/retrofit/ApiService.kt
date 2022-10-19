package com.raspisanie.mai.data.net.retrofit

import com.raspisanie.mai.data.net.models.AdvCreateBody
import com.raspisanie.mai.data.net.models.FeedbackBody

class ApiService(private var api: Api) {
    suspend fun getGroupList(name: String) = api.getGroupList(name)

    suspend fun getSchedule(id: String) = api.getSchedule(id)

    suspend fun sendFeedback(body: FeedbackBody) = api.sendFeedback(body)

    suspend fun getAdvPage(limit: Int, skip: Int) = api.getAdvPage(limit, skip)

    suspend fun getCanteens() = api.getCanteens()

    suspend fun getStudents() = api.getStudents()

    suspend fun getDevList() = api.getDevList()

    suspend fun getSport() = api.getSport()

    suspend fun getLibrary() = api.getLibrary()

    suspend fun getCreative() = api.getCreative()

    suspend fun getNotifications(date: String) = api.getNotifications(date)

    suspend fun getNews(limit: Int, skip: Int) = api.getNews(limit, skip)

    suspend fun likeNews(id: String) = api.likeNews(id)

    suspend fun likeAdv(id: String) = api.likeAdv(id)

    suspend fun getSearchLector(name: String) = api.getSearchLector(name)

    suspend fun getLectorSchedule(id: String) = api.getLectorSchedule(id)

    suspend fun createAdv(body: AdvCreateBody) = api.createAdv(body)

    suspend fun getFeedbackResponse() = api.getFeedbackResponse()
}