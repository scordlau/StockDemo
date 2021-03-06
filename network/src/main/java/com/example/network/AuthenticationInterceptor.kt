package com.example.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
                .addHeader("API_KEY", "KEY")
                .build()

        return chain.proceed(newRequest)
    }

}
