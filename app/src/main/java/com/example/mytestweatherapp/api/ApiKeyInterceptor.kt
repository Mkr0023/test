package com.example.mytestweatherapp.api

import androidx.annotation.NonNull
import com.example.mytestweatherapp.utils.METRICS
import com.example.mytestweatherapp.utils.OPEN_WEATHER_API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("appid", OPEN_WEATHER_API_KEY)
            .addQueryParameter("units", METRICS)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {

        @NonNull
        fun create(): Interceptor {
            return ApiKeyInterceptor()
        }
    }
}