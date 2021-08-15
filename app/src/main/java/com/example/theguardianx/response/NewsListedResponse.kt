package com.example.theguardianx.response

import java.io.Serializable

class NewsListedResponse( val response : GuardianResponse) {


    data class GuardianResponse(val status : String, val userTier: String, val results: ArrayList<News>, val pages: Int, val currentPage: Int, val pageSize: Int) : Serializable

    data class News(val id: String, val webPublicationDate: String, val apiUrl: String, val fields: FieldsToUse) : Serializable

    data class  FieldsToUse(
        val thumbnail: String,
        val headline: String,
        val bodyText: String,
        val trailText: String,
        val byline: String) : Serializable
}