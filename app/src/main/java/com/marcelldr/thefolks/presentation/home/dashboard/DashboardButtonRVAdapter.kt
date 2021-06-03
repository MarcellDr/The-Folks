package com.marcelldr.thefolks.presentation.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelldr.thefolks.databinding.RvDashboardButtonItemBinding
import com.marcelldr.thefolks.domain.model.DashboardButton

class DashboardButtonRVAdapter(private val listDashboardButton: List<DashboardButton>) :
    RecyclerView.Adapter<DashboardButtonRVAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: RvDashboardButtonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dashboardButton: DashboardButton) {
            with(binding) {
                rvDashboardButtonTitle.text = dashboardButton.title
                Glide.with(rvDashboardButtonImage.context).load(dashboardButton.imageDrawable)
                    .into(rvDashboardButtonImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            RvDashboardButtonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDashboardButton[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDashboardButton[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listDashboardButton.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(dashboardButton: DashboardButton)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}