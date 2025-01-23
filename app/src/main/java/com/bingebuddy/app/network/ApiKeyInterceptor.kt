package com.bingebuddy.app.network

import com.bingebuddy.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val token = BuildConfig.API_KEY

        // Add token to the request header if available
        val authenticatedRequest = token.let {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        } ?: originalRequest

        val response = chain.proceed(request = authenticatedRequest)

        return response
    }

}