package pro.midev.juttermap.server

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Интерсептор для вставки апи ключа от выбранной карты.
 */
class JTTokenInterceptor(val key: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request: Request = original

        request = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("ApiKey", key)
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}