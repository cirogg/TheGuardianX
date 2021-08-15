package com.example.theguardianx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theguardianx.response.NewsListedResponse
import kotlinx.android.synthetic.main.card_news_listed.view.*

class NewsAdapter(val onTap : (NewsListedResponse.News) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var arrayOfData = arrayListOf<NewsListedResponse.News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_news_listed,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val item = arrayOfData[position]

        holder.apply {
            headline.text = item.fields.headline.toString()
            Glide.with(thumbnail.context).load(item.fields.thumbnail).into(thumbnail)
            setFields(item)
        }


    }

    override fun getItemCount(): Int {
        return arrayOfData.size
    }

    fun setNews(newArray : ArrayList<NewsListedResponse.News>){
        arrayOfData = newArray
        notifyDataSetChanged()
    }

    fun addNews(newArray : ArrayList<NewsListedResponse.News>){
        arrayOfData.addAll(newArray)
        notifyDataSetChanged()
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val headline: TextView = itemView.textViewTitle
        val thumbnail: ImageView = itemView.thumbnail

        fun setFields (fields : NewsListedResponse.News){
            itemView.setOnClickListener{onTap(fields)}
        }
    }


}