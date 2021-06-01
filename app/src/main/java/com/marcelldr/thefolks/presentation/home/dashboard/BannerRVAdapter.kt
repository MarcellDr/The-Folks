package com.marcelldr.thefolks.presentation.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelldr.thefolks.databinding.RvBannerItemBinding
import com.marcelldr.thefolks.domain.model.Banner

class BannerRVAdapter(private val listBanner: List<Banner>) :
    RecyclerView.Adapter<BannerRVAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: RvBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) {
            with(binding) {
                Glide.with(rvBannerImage.context).load(banner.imageUrl).into(rvBannerImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            RvBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBanner[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listBanner[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(banner: Banner)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}