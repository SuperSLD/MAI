package pro.midev.juttermap.models

data class JTDataWrapper<T>(
        var success: Boolean,
        var message: String?,
        var data: T?
)