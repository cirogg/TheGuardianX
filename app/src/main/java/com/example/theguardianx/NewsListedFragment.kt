package com.example.theguardianx

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theguardianx.response.NewsListedResponse
import com.example.theguardianx.viewmodels.NewsViewModel
import com.example.theguardianx.viewmodels.StateViewModel
import kotlinx.android.synthetic.main.fragment_news_listed.*
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback


class NewsListedFragment : Fragment() {

    private lateinit var viewModelNews : NewsViewModel
    private val adapter by lazy { NewsAdapter{ news ->
        viewModelNews.getIndividualNews(news.apiUrl)
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_listed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewModelNews = ViewModelProvider(requireActivity(), StateViewModel.Factory(requireActivity(),savedInstanceState)).get(NewsViewModel::class.java)

        initRW()
        initObservers()

        viewModelNews.getNews(1)

        recyclerViewNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    moreNews()
                }
            }
        })


    }


    // get more news
    private fun moreNews(){
        viewModelNews.getNews(viewModelNews.newsListedResponse.value!!.value!!.response.currentPage + 1)
    }

    private fun initRW(){
        recyclerViewNews.adapter = adapter
        recyclerViewNews.layoutManager = LinearLayoutManager(context)
    }

    private fun initObservers (){
        viewModelNews.newsListedResponse.observe(viewLifecycleOwner, { news ->
            news.let {
                if (it.errorResponse != null){
                    adapter.setNews(arrayListOf())
                    Toast.makeText(requireContext(), it.errorResponse!!.message.toString(), Toast.LENGTH_LONG).show()
                }else{
                    //200 ok
                      if (it.value!!.response.currentPage == 1){
                          adapter.addNews(news.value!!.response.results)
                      }else{
                          adapter.addNews(news.value!!.response.results)
                          recyclerViewNews.smoothScrollToPosition(recyclerViewNews.adapter!!.itemCount - (news.value.response.pageSize -1))
                      }
                }
            }
        })


        //Individual news navigate
        viewModelNews.individualNewsResponse.observe(viewLifecycleOwner, {individualResponse ->
            individualResponse?.let {
                if (it.errorResponse != null){
                    Toast.makeText(requireContext(), it.errorResponse!!.message.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.mainNews_to_detail)
                }
            }
        })
    }


}