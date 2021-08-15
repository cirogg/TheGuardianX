package com.example.theguardianx.service

import com.example.theguardianx.service.Const.Companion.APIKEY
import okhttp3.Interceptor
import okhttp3.Response


/** Interceptor to add header to all calls easily */
class RetrofitInterceptor :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("api-key", APIKEY)
                .build()
        )
    }

}