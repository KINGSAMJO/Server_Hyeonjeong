package com.example.seminar_task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.seminar_task1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTransaction()
    }

    private fun initTransaction(){
        val fragment1 = FollowerFragment()
        val fragment2 = RepositoryFragment()

        supportFragmentManager.beginTransaction().add(R.id.fcv_home,fragment1).commit()
        binding.btnFollower.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fcv_home,fragment1).commit()
        }
        binding.btnRepository.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fcv_home,fragment2).commit()
        }
    }


}