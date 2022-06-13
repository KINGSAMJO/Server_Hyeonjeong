package com.example.seminar_task1.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seminar_task1.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    
}