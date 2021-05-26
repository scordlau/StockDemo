package com.example.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by scordlau on 5/24/21.
 */

class RetrofitModule<T : Any> {

    fun createApiService(serviceClazz: Class<T>, baseUrl: String): T? =
            createRetrofit(baseUrl)
                    .create(serviceClazz)

    private fun createRetrofit(baseUrl: String): Retrofit {
        val client = createOkHttpClient()
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(
                        HTTP_TIMEOUT.toLong(),
                        TimeUnit.MILLISECONDS
                )
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })


        return builder.build()
    }

    companion object {
        const val HTTP_TIMEOUT = 30000
    }
}