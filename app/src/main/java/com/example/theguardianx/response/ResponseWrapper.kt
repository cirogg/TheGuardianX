package com.example.theguardianx.response

import java.io.Serializable

class ResponseWrapper<T>(val value: T?) : Serializable {
    var errorResponse: ErrorResponse? = null

    constructor(code: String?, error: String?) : this (null){
        errorResponse = ErrorResponse().apply {
            this.code = code
            this.message = error
        }
    }
}