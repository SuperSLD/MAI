package com.raspisanie.mai.models.server

data class AdvCreateBody(
    val name: String,
    val lastname: String,
    val text: String,
    val vk: String,
    val tg: String,
    val other: String
)