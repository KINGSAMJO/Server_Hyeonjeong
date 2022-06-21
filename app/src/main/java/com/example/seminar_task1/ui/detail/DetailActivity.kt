package com.example.seminar_task1.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.seminar_task1.databinding.ActivityDetailBinding
import org.w3c.dom.Text


class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserInformation() //리사이클러뷰에서 가져오 데이터 할당해줌
    }

    private fun getUserInformation(){
        val userName = intent.getStringExtra("name")
        val userImage = intent.getStringExtra("image")
        binding.tvDetailNames.text = userName
        Glide.with(this)
            .load(userImage)
            .into(binding.ivDetailProfile)
    }

}