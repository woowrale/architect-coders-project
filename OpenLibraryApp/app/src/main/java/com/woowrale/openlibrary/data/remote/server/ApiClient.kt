package com.woowrale.openlibrary.data.remote.server

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.woowrale.openlibrary.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient {

    private val REQTIMEOUT = 600
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    fun getClient(): Retrofit {
        if (okHttpClient == null) {
            initOkHttp()
        }
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(
                REQTIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                REQTIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .writeTimeout(
                REQTIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader(BuildConfig.HEADER_ACCEPT, BuildConfig.HEADER_ACCEPT_VALUE)
                    .addHeader(BuildConfig.HEADER_REQUEST, BuildConfig.HEADER_REQUEST_VALUE)
                    .addHeader(BuildConfig.HEADER_CONTENT, BuildConfig.HEADER_CONTENT_VALUE)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        okHttpClient = httpClient.build()
    }

}