package com.raspisanie.mai.data.net

data class DataWrapper<T>(
        var success: Boolean,
        var message: String?,
        var data: T?
)