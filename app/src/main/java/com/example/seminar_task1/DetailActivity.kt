package com.example.seminar_task1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.seminar_task1.databinding.ActivityDetailBinding
import org.w3c.dom.Text


class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var tvName : TextView
    private lateinit var tvDesc : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvName = binding.tvDetailNames
        tvDesc = binding.tvDetailDescription
        rvIntent() //리사이클러뷰에서 가져오 데이터 할당해줌
    }

    private fun rvIntent(){
        val intent = intent
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("desc")
        tvName.setText(name)
        tvDesc.setText(description)
    }

}