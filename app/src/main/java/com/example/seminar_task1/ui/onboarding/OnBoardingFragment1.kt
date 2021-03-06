package com.example.seminar_task1.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.seminar_task1.R
import com.example.seminar_task1.databinding.FragmentOnBoarding1Binding


class OnBoardingFragment1 : Fragment() {
    private var _binding : FragmentOnBoarding1Binding? = null
    private val binding get() = _binding ?: error("onBoardingFragment1 binding 설정 안됨")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding1Binding.inflate(layoutInflater,container, false)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment1_to_onBoardingFragment2)
        }

        return binding.root
    }

}