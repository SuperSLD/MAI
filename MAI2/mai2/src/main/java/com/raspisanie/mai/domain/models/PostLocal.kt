package com.raspisanie.mai.domain.models

/**
 * Общий интерфейс для
 * элементов которые содержат информацию
 * о лайках и просмотрах.
 */
interface PostLocal {
    fun getRecordId(): String
    fun getLikes(): Int
    fun isLiked(): Boolean
    fun getViews(): Int
    fun getDate(): String
}