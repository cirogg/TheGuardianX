package com.example.theguardianx.service

import androidx.lifecycle.MutableLiveData
import com.example.theguardianx.response.IndividualNewsResponse
import com.example.theguardianx.response.NewsListedResponse
import com.example.theguardianx.response.ResponseWrapper
import javax.security.auth.callback.Callback

class NewsRepository {

    var isLoading = MutableLiveData<Boolean>()
    var newsResposne = MutableLiveData<ResponseWrapper<NewsListedResponse>>()
    var individualNewsResponse = MutableLiveData<ResponseWrapper<IndividualNewsResponse>>()


//    fun getNews(news : String, page : Int){
    fun getNews( page : Int){
        isLoading.value = true
        RetrofitInst.theGuardianApi.getNews(page).enqueue(CallBackPlus<NewsListedResponse>().apply {
            onSuccess = {
                newsResposne.value = ResponseWrapper(it)
            }
            onServerError = { c,e ->
                newsResposne.value = ResponseWrapper(c,e)
            }
            onNetworkError = {
                newsResposne.value = ResponseWrapper("00000","Network Error")
            }
            onFinish = {
                isLoading.value = false
            }
        })
    }


    fun getIndividualNews(url : String){
        isLoading.value = true
        RetrofitInst.theGuardianApi.getIndividualNews("$url?show-fields=all").enqueue(CallBackPlus<IndividualNewsResponse>().apply {
            onSuccess = {
                individualNewsResponse.value = ResponseWrapper(it)
            }
            onNetworkError = {individualNewsResponse.value = ResponseWrapper("00000","Network Error")}
            onServerError = { c,e ->
                individualNewsResponse.value = ResponseWrapper(c,e)
            }
            onFinish = {isLoading.value = false}
        })
    }

}