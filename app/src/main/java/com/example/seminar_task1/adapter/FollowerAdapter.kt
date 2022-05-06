package com.example.seminar_task1.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.DetailActivity
import com.example.seminar_task1.model.FollowerData
import com.example.seminar_task1.HomeActivity
import com.example.seminar_task1.databinding.ItemFollowerListBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    val followerList = mutableListOf<FollowerData>()
    private var homeActivity : HomeActivity? = null
    private lateinit var mContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        mContext = parent.context
        homeActivity = HomeActivity()
        val binding = ItemFollowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", followerList[position].followerName)
            intent.putExtra("desc", followerList[position].followerIntro)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return followerList.size
    }

    class FollowerViewHolder(private val binding : ItemFollowerListBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerData){
            binding.tvFollowerListName.text = data.followerName
            binding.tvFollowerListIntro.text = data.followerIntro

        }

    }
}