package com.example.seminar_task1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.databinding.ItemRepositoryListBinding

class RepositoryAdapter:RecyclerView.Adapter<RepositoryAdapter.RepositoryVieHolder>(){
    val repoList = mutableListOf<RepositoryData>()
    private lateinit var mContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryAdapter.RepositoryVieHolder {
        mContext = parent.context
        val binding = ItemRepositoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryVieHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.RepositoryVieHolder, position: Int) {
        holder.onBind(repoList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,DetailActivity::class.java)
            intent.putExtra("name", repoList[position].repoName)
            intent.putExtra("desc", repoList[position].repoIntro)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    class RepositoryVieHolder(private val binding : ItemRepositoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RepositoryData){
            binding.tvRepoListName.text = data.repoName
            binding.tvRepoListIntro.text = data.repoIntro
        }

    }
}