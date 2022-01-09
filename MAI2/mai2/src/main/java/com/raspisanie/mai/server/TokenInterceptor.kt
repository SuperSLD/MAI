package com.raspisanie.mai.server

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request: Request = original

        request = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("ApiKey", "3b0bf028-7be4-4a88-bd79-fcfcf33e6ded")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}