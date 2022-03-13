package com.raspisanie.mai.models.local

import com.raspisanie.mai.extesions.fromFormatToFormat

data class NewsLocal(
    val id: String,
    val title: String,
    val author: String,
    val text: String,
    val warning: Boolean,
    val createdAt: String,
    val image: String?,
    val imageDark: String?,
    var likeCount: Int,
    var isLike: Boolean,
    val viewCount: Int,
) : PostLocal {

    override fun getRecordId() = id
    override fun getLikes() = likeCount
    override fun isLiked() = isLike
    override fun getViews() = viewCount
    override fun getDate() =
        createdAt.split(".")[0].fromFormatToFormat("yyyy-MM-dd HH:mm:ss", "HH:mm dd.MM.yyyy")
}