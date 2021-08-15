package com.example.theguardianx

import java.io.Serializable

class IndividualNews (
    val title : String,
    val date : String,
    val headline : String,
    val body: String,
    val thumbnail : String?,
    val author : String?) : Serializable