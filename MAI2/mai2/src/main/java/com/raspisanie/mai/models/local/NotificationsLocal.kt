package com.raspisanie.mai.models.local

import com.raspisanie.mai.extesions.toFormat
import java.util.*
import kotlin.collections.HashMap

data class NotificationsLocal(
    var lastUpdate: String,
    var counts: HashMap<String, Int>
) {
    companion object {
        const val NEWS_COUNT = "news_counts"
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

    /**
     * Замена старой даты на текущую
     */
    fun updateDate() {
        lastUpdate = Calendar.getInstance().toFormat("dd.MM.yyyy_HH:mm")
    }
}