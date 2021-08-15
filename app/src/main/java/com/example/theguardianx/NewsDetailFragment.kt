package com.example.theguardianx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.theguardianx.viewmodels.NewsViewModel
import com.example.theguardianx.viewmodels.StateViewModel
import kotlinx.android.synthetic.main.card_news_listed.*
import kotlinx.android.synthetic.main.fragment_news_detail.*


class NewsDetailFragment : Fragment() {

    private lateinit var  viewModel : NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), StateViewModel.Factory(requireActivity(), savedInstanceState)).get(NewsViewModel::class.java)

        textViewTitleIndividual.text = viewModel.tappedNews.value!!.title
        textViewDate.text = viewModel.tappedNews.value!!.date
        textViewAuthor.text = viewModel.tappedNews.value!!.author
        textViewSubtitle.text = viewModel.tappedNews.value!!.headline
        textViewBody.text = viewModel.tappedNews.value!!.body

        Glide.with(imageIndividualNews.context).load(viewModel.tappedNews.value!!.thumbnail).into(imageIndividualNews)

    }

}