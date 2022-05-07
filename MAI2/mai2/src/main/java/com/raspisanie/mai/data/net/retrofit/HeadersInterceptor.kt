package com.raspisanie.mai.data.net.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.raspisanie.mai.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HeadersInterceptor(val context: Context) : Interceptor {

    @SuppressLint("HardwareIds")
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request: Request = original

        val deviceId = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ANDROID_ID);

        request = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("ApiKey", "3b0bf028-7be4-4a88-bd79-fcfcf33e6ded")
            .header("DeviceId", deviceId)
            .header("BuildCode", BuildConfig.VERSION_CODE.toString())
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}