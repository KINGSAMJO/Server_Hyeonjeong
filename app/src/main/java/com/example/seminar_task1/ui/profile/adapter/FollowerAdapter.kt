package com.example.seminar_task1.ui.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_task1.data.model.FollowerData
import com.example.seminar_task1.data.model.response.ResponseGithubFollowersItem
import com.example.seminar_task1.ui.HomeActivity
import com.example.seminar_task1.databinding.ItemFollowerListBinding

// Lambda
// fragment에서 생성자 호출하여 itemClick 정보 받음
// -> onCreatViewholder에서 생성자에 있는 인자를 뷰홀더 생성하면서 넘긴
// -> FollowerViewHolder에서 현재 위치인 binding.root에 클릭이벤트 발생시 fragment에서 받은 함수 실행됨
class FollowerAdapter(private val itemClick: (ResponseGithubFollowersItem) -> (Unit)) :
    ListAdapter<ResponseGithubFollowersItem, FollowerAdapter.FollowerViewHolder>(FollowerDiffUtil) {
    //생성자에 putExtra로 전달한 값 받음
    val followerList = mutableListOf<ResponseGithubFollowersItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            ItemFollowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding, itemClick) //viewholder 생성할 때 itemCLick 전달
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])
    }

    override fun getItemCount(): Int {
        return followerList.size
    }

    class FollowerViewHolder(
        private val binding: ItemFollowerListBinding,
        private val itemClick: (ResponseGithubFollowersItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGithubFollowersItem) {
            binding.followerItem = data
            binding.root.setOnClickListener {  //호출시 itemclick 람다함수실행함
                itemClick(data)
            }

        }

    }
}
object FollowerDiffUtil : DiffUtil.ItemCallback<ResponseGithubFollowersItem>() {

    override fun areItemsTheSame(oldItem: ResponseGithubFollowersItem, newItem: ResponseGithubFollowersItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResponseGithubFollowersItem, newItem: ResponseGithubFollowersItem): Boolean {
        return oldItem == newItem
    }
}
