package com.raspisanie.mai.models.server

data class NewsResponse(
    val id: String,
    val title: String,
    val author: String,
    val text: String,
    val warning: Boolean,
    val createdAt: String,
    val image: String?,
)