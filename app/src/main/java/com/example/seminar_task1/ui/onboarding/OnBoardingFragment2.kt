package com.example.seminar_task1.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.seminar_task1.R
import com.example.seminar_task1.databinding.FragmentOnBoarding2Binding


class OnBoardingFragment2 : Fragment() {
    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding get() = _binding ?: error("onBoardingFragment2 binding 설정 안됨")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding2Binding.inflate(layoutInflater, container, false)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment2_to_onBoardingFragment3)
        }

        return binding.root

    }
}