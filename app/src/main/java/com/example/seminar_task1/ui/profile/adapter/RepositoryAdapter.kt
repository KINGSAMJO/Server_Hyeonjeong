package com.example.seminar_task1.ui.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.data.model.RepositoryData
import com.example.seminar_task1.data.model.response.ResponseGithubFollowersItem
import com.example.seminar_task1.data.model.response.ResponseGithubRepositoryItem
import com.example.seminar_task1.databinding.ItemRepositoryListBinding
import com.example.seminar_task1.ui.detail.DetailActivity


class RepositoryAdapter(private val itemClick: (ResponseGithubRepositoryItem) -> (Unit)) :
   ListAdapter<ResponseGithubRepositoryItem, RepositoryAdapter.RepositoryViewHolder>(RepositoryDiffUtil) {
    val repoList = mutableListOf<ResponseGithubRepositoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(repoList[position])

    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    class RepositoryViewHolder(
        private val binding: ItemRepositoryListBinding,
        private val itemClick: (ResponseGithubRepositoryItem) -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(data: ResponseGithubRepositoryItem) {
            binding.repositoryItem = data
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }


    }
}
object RepositoryDiffUtil : DiffUtil.ItemCallback<ResponseGithubRepositoryItem>() {

    override fun areItemsTheSame(oldItem: ResponseGithubRepositoryItem, newItem: ResponseGithubRepositoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResponseGithubRepositoryItem, newItem: ResponseGithubRepositoryItem): Boolean {
        return oldItem == newItem
    }
}