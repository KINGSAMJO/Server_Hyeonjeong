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

// Lambda
// fragment에서 생성자 호출하여 itemClick 정보 받음
// -> onCreatViewholder에서 생성자에 있는 인자를 뷰홀더 생성하면서 넘긴
// -> FollowerViewHolder에서 현재 위치인 binding.root에 클릭이벤트 발생시 fragment에서 받은 함수 실행됨
class FollowerAdapter(private val itemClick: (FollowerData) -> (Unit)) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    //생성자에 putExtra로 전달한 값 받음
    val followerList = mutableListOf<FollowerData>()
    private var homeActivity: HomeActivity? = null
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        mContext = parent.context
        homeActivity = HomeActivity()
        val binding = ItemFollowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding, itemClick) //viewholder 생성할 때 itemCLick 전달
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])

        /*holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", followerList[position].followerName)
            intent.putExtra("desc", followerList[position].followerIntro)
            mContext.startActivity(intent)
        }*/

    }

    override fun getItemCount(): Int {
        return followerList.size
    }

    class FollowerViewHolder(
        private val binding: ItemFollowerListBinding,
        private val itemClick: (FollowerData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerData) {
            binding.tvFollowerListName.text = data.followerName
            binding.tvFollowerListIntro.text = data.followerIntro

            binding.root.setOnClickListener {  //호출시 itemclick 람다함수실행함
                itemClick(data)
            }

        }

    }
}