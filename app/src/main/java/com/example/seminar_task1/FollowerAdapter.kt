package com.example.seminar_task1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.databinding.ItemFollowerListBinding
import java.util.ArrayList

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    val followerList = mutableListOf<FollowerData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.FollowerViewHolder {
        val binding = ItemFollowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])
    }

    override fun getItemCount(): Int {
        return followerList.size
    }

    class FollowerViewHolder(private val binding : ItemFollowerListBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data:FollowerData){
            binding.tvFollowerListName.text = data.followerName
            binding.tvFollowerListIntro.text = data.followerIntro
        }

    }
}