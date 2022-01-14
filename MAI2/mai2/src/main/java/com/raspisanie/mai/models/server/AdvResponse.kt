package com.raspisanie.mai.models.server

data class AdvResponse(
    val id: String,
    val name: String,
    val lastname: String,
    val text: String,
    val status: Int,
    val vk: String,
    val tg: String,
    val other: String,
    val createdAt: String,
    val likes: Int,
    val views: Int,
    val like: Boolean,
)