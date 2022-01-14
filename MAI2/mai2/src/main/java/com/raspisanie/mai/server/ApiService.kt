package com.raspisanie.mai.server

import com.raspisanie.mai.models.server.AdvCreateBody
import com.raspisanie.mai.models.server.FeedbackBody
import com.raspisanie.mai.server.Api

class ApiService(private var api: Api) {
    fun getGroupList(name: String) = api.getGroupList(name)

    fun getSchedule(id: String) = api.getSchedule(id)

    fun sendFeedback(body: FeedbackBody) = api.sendFeedback(body)

    fun getAdvPage(limit: Int, skip: Int) = api.getAdvPage(limit, skip)

    fun getCanteens() = api.getCanteens()

    fun getStudents() = api.getStudents()

    fun getDevList() = api.getDevList()

    fun getSport() = api.getSport()

    fun getLibrary() = api.getLibrary()

    fun getCreative() = api.getCreative()

    fun getNotifications(date: String) = api.getNotifications(date)

    fun getNews(limit: Int, skip: Int) = api.getNews(limit, skip)

    fun getSearchLector(name: String) = api.getSearchLector(name)

    fun getLectorSchedule(id: String) = api.getLectorSchedule(id)

    fun createAdv(body: AdvCreateBody) = api.createAdv(body)
}