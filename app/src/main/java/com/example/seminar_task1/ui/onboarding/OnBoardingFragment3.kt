package com.example.seminar_task1.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seminar_task1.ui.signin.SignInActivity
import com.example.seminar_task1.databinding.FragmentOnBoarding3Binding

class OnBoardingFragment3 : Fragment() {
    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding ?: error("onBoardingFragment3 binding 설정 안됨")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding3Binding.inflate(layoutInflater, container, false)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            activity?.finish()

        }
        return binding.root
    }

}