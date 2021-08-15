package com.example.theguardianx.response

import java.io.Serializable

data class IndividualNewsResponse(val response : ContentResposne) : Serializable
data class ContentResposne (val content : NewsListedResponse.News) : Serializable