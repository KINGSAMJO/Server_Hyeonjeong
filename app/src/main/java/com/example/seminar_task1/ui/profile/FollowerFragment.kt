package com.example.seminar_task1.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.seminar_task1.R
import com.example.seminar_task1.databinding.FragmentFollowerBinding
import com.example.seminar_task1.ui.base.BaseFragment
import com.example.seminar_task1.ui.detail.DetailActivity
import com.example.seminar_task1.ui.profile.adapter.FollowerAdapter
import com.example.seminar_task1.ui.viewmodel.FollowerViewModel
import com.example.seminar_task1.util.ItemDecoration

class FollowerFragment(private val userLogin: String) :
    BaseFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {
    private val followerViewModel by viewModels<FollowerViewModel>()
    private lateinit var followerAdapter: FollowerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        initAdapter()
        binding.followViewModel = followerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollower.addItemDecoration(ItemDecoration(10, "#FFBB86FC", 10))

    }

    private fun initAdapter() {
        followerViewModel.followerNetwork(userLogin) //userName 넘겨줘서 서버 연결시 user follower만 보일 수 있도록 함

        followerAdapter = FollowerAdapter { // itemclick 람다함수에서 사용할 인자 값을 전달해줌(원래는 어뎁터만 연결해줬음)
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.apply {//puExtra 묶어서 전달
                intent.putExtra("name", it.login)
                intent.putExtra("image", it.avatar_url)
            }
            startActivity(intent)
        }
        binding.rvFollower.adapter = followerAdapter

        followerViewModel.followerData.observe(viewLifecycleOwner) {
            val followers = it
            for (i in followers) {
                followerAdapter.followerList.add(i)
            }
        }

        followerAdapter.notifyDataSetChanged()

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}