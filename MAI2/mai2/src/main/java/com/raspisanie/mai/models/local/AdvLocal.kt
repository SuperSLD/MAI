package com.raspisanie.mai.models.local

data class AdvLocal(
    val id: String,
    val name: String,
    val lastname: String,
    val text: String,
    val status: Int,
    val vk: String,
    val tg: String,
    val other: String,
    val createdAt: String,
    var likeCount: Int,
    var isLike: Boolean,
    val viewCount: Int,
) : PostLocal {

    override fun getRecordId() = id
    override fun getLikes() = likeCount
    override fun isLiked() = isLike
    override fun getViews() = viewCount
    override fun getDate() = ""
}