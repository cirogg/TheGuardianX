package com.example.theguardianx.service

import com.example.theguardianx.response.ErrorResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class CallBackPlus<T> : retrofit2.Callback<T> {

    private var isSuccessful = false
    var onSuccess: ((T?) -> Unit)? = null
    var onNetworkError: (() -> Unit)? = null
    var onServerError: ((String?, String?) -> Unit)? = null
    var onFinish: ((Boolean?) -> Unit)? = null

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful){
            isSuccessful = true
            onSuccess?.invoke(response.body())
            response.headers()

        } else{
            val errorResponse: ErrorResponse? = getErrorResponse(response)
            onServerError?.invoke(response.code().toString(), errorResponse?.message)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onNetworkError?.invoke()
        onFinish?.invoke(isSuccessful)
    }

    private fun getErrorResponse(response: Response<T>): ErrorResponse? {
        try {
            return GsonConverterFactory.create().responseBodyConverter(
                ErrorResponse::class.java!!,
                arrayOfNulls(0),
                null)?.convert(response.errorBody()!!) as ErrorResponse?

        } catch (e: IOException){
            e.printStackTrace()
        }
        return null
    }

}