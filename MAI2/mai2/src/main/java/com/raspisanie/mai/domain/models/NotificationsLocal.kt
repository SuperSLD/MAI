package com.raspisanie.mai.domain.models

import com.raspisanie.mai.extesions.toFormat
import java.util.*
import kotlin.collections.HashMap

data class NotificationsLocal(
    var lastUpdate: String,
    var counts: HashMap<String, Int>
) {
    companion object {
        const val NEWS_COUNT = "news_counts"
        const val SUPPORT_COUNT = "support_counts"
    }

    /**
     * Получение количества новостей
     */
    fun getNewsCount() = counts[NEWS_COUNT] ?: 0

    /**
     * Изменение количества новостей
     */
    fun setNewsCount(count: Int) {
        counts[NEWS_COUNT] = count
    }

    fun setSupportCount(count: Int) {
        counts[SUPPORT_COUNT] = count
    }

    fun getSupportCount() = counts[SUPPORT_COUNT] ?: 0

    /**
     * Замена старой даты на текущую
     */
    fun updateDate() {
        lastUpdate = Calendar.getInstance().toFormat("dd.MM.yyyy_HH:mm")
    }
}