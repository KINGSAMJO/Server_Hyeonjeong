package com.example.seminar_task1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.seminar_task1.databinding.ActivityDetailBinding
import org.w3c.dom.Text


class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvIntent() //리사이클러뷰에서 가져오 데이터 할당해줌
    }

    private fun rvIntent(){
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("desc")
        binding.tvDetailNames.setText(name)
        binding.tvDetailDescription.setText(description)
    }

}