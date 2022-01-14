package com.raspisanie.mai.models.local

data class NewsLocal(
    val id: String,
    val title: String,
    val author: String,
    val text: String,
    val warning: Boolean,
    val createdAt: String,
    val image: String?,
)