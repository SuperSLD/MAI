package com.raspisanie.mai.server

import com.raspisanie.mai.server.Api

class ApiService(private var api: Api) {
    fun getGroupList(name: String) = api.getGroupList(name)

    fun getSchedule(id: String) = api.getSchedule(id)
}