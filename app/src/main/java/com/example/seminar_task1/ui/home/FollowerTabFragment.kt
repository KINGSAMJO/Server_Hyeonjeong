package com.example.seminar_task1.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seminar_task1.databinding.FragmentFollowerTabBinding


class FollowerTabFragment : Fragment() {
    private var _binding : FragmentFollowerTabBinding? = null
    private val binding get() = _binding ?: error("FollowerInHomeFragment 오류")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerTabBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}