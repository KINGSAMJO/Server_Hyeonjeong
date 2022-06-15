package com.example.seminar_task1.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.DetailActivity
import com.example.seminar_task1.model.RepositoryData
import com.example.seminar_task1.databinding.ItemRepositoryListBinding
import com.example.seminar_task1.model.FollowerData


class RepositoryAdapter(private val mContext: Context?) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    val repoList = mutableListOf<RepositoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(repoList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", repoList[position].repoName)
            intent.putExtra("desc", repoList[position].repoIntro)
            mContext?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    class RepositoryViewHolder(private val binding: ItemRepositoryListBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun onBind(data: RepositoryData) {
            binding.tvRepoListName.text = data.repoName
            binding.tvRepoListIntro.text = data.repoIntro
        }

    }
}