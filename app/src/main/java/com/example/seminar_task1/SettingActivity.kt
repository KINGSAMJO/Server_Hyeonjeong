package com.example.seminar_task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seminar_task1.databinding.ActivitySettingBinding
import com.example.seminar_task1.util.SOPTSharedPreferences

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isAutoLogOut()
    }

    private fun isAutoLogOut(){
        binding.clAutoLogout.setOnClickListener{
            SOPTSharedPreferences.setAutoLogin(false)
            SOPTSharedPreferences.setLogout(this)
            Toast.makeText(this,"자동로그인 되었습니다",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SettingActivity, SignInActivity::class.java))
            finish()
        }
    }
}