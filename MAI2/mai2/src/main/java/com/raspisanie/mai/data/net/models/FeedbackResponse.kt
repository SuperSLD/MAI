package com.raspisanie.mai.data.net.models

data class FeedbackResponse(
     val id: String,
     val name: String,
     val email: String,
     val message: String,
     val version: String,
     val number: String,
     val platform: String,
     val deviceId: String,
     val createdAt: String,
     val response: List<Response>
)

data class Response(
     val id: String,
     val response: String,
     val createdAt: String,
)