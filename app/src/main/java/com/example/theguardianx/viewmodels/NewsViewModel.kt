package com.example.theguardianx.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.example.theguardianx.IndividualNews
import com.example.theguardianx.response.IndividualNewsResponse
import com.example.theguardianx.response.ResponseWrapper
import com.example.theguardianx.service.NewsRepository

class NewsViewModel (state : SavedStateHandle) : StateViewModel(state){

    private val repo = NewsRepository()

    val newsListedResponse = Transformations.map(repo.newsResposne){it}
    val tappedNews = MutableLiveData<IndividualNews>()
    val individualNewsResponse = MutableLiveData<ResponseWrapper<IndividualNewsResponse>>()

    init {
        repo.individualNewsResponse.observeForever{
            it?.let{
                individualNewsResponse.value = it
                tappedNews.value = IndividualNews(it.value!!.response.content.fields.headline,
                                                it.value.response.content.webPublicationDate,
                                                it.value.response.content.fields.trailText,
                                                it.value.response.content.fields.bodyText,
                                                it.value.response.content.fields.thumbnail,
                                                it.value.response.content.fields.byline)
                individualNewsResponse.value = null
            }
        }
    }


    fun getNews(page : Int = 1){
        repo.getNews(page)
    }

    fun getIndividualNews(url : String){
        repo.getIndividualNews(url)
    }


}