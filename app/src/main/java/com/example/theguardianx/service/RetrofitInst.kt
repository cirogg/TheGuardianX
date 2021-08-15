package com.example.theguardianx.service

import com.example.theguardianx.BuildConfig
import com.example.theguardianx.service.Const.Companion.BASE_URL_GUARDIAN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInst {

    private val READ_TIMEOUT = 40
    private var okHttpClientInstance: OkHttpClient? = null
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofitNews by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_GUARDIAN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    val theGuardianApi : NewsGuardianApi by lazy {
        retrofitNews.create(NewsGuardianApi::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (okHttpClientInstance == null) {
            okHttpClientInstance = getBaseBuilder().build()
        }
        return okHttpClientInstance!!
    }

    private fun getBaseBuilder(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(RetrofitInterceptor())

        //show log with useful info (only on debug)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        return okHttpClientBuilder
    }

}