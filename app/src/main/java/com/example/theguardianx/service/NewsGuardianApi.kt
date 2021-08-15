package com.example.theguardianx.service

import com.example.theguardianx.response.IndividualNewsResponse
import com.example.theguardianx.response.NewsListedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsGuardianApi {

    @GET("search?show-fields=thumbnail,headline")
    fun getNews(@Query("page") page : Int): Call<NewsListedResponse>

    @GET
    fun getIndividualNews(@Url apiurl: String) : Call<IndividualNewsResponse>

}