package com.raspisanie.mai.models.server

data class NewsResponse(
    val id: String,
    val title: String,
    val author: String,
    val text: String,
    val warning: Boolean,
    val createdAt: String,
    val image: String?,
    val imageDark: String?,
    val likes: Int,
    val views: Int,
    val like: Boolean,
)