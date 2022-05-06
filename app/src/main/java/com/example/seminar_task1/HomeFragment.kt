package com.example.seminar_task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seminar_task1.adapter.FollowTabViewPagerAdapter
import com.example.seminar_task1.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("HomeFragment Binding 오류")
    private lateinit var followTabViewpagerAdapter : FollowTabViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initTabLayout()
    }

    /**
     * viewpager에 어뎁터 연결
     */
    private fun initAdapter(){
        var fragmentList = listOf(FollowerTabFragment(), FollowingTabFragment())

        followTabViewpagerAdapter = FollowTabViewPagerAdapter(this)
        followTabViewpagerAdapter.fragments.addAll(fragmentList)

        binding.vpFragHome.adapter = followTabViewpagerAdapter
    }

    /**
     * viewpager와 TabLayout 연동
     */
    private fun initTabLayout(){
        val tabLabel = listOf("Following", "Follower")

        TabLayoutMediator(binding.tlFragHome, binding.vpFragHome) { tab, position ->
            tab.text = tabLabel[position] //동적으로 Tablayout 설정
        }.attach()
    }

}