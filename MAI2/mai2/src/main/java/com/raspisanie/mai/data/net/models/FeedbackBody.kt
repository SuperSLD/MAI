package com.raspisanie.mai.data.net.models

import com.raspisanie.mai.BuildConfig
import okhttp3.internal.platform.Platform

data class FeedbackBody(
        val name: String,
        val email: String,
        val message: String,
        val platform: String = "android",
        val number: String = BuildConfig.VERSION_CODE.toString(),
        val version: String = BuildConfig.VERSION_NAME
)