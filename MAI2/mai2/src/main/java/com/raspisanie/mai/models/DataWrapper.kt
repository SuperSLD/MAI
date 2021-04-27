package com.raspisanie.mai.models

data class DataWrapper<T>(
        var success: Boolean,
        var message: String?,
        var data: T?
)