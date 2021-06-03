package com.marcelldr.thefolks.presentation.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelldr.thefolks.databinding.RvBannerItemBinding
import com.marcelldr.thefolks.databinding.RvNewsItemBinding
import com.marcelldr.thefolks.domain.model.Banner
import com.marcelldr.thefolks.domain.model.News

class NewsRVAdapter(private val listNews: List<News>) :
    RecyclerView.Adapter<NewsRVAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    inner class ListViewHolder(private val binding: RvNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            with(binding) {
                rvNewsTitle.text = news.title
                rvNewsDescription.text = news.description
                Glide.with(rvNewsImage.context).load(news.urlToImage).into(rvNewsImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            RvNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNews[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listNews[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: News)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}