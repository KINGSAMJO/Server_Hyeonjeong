package com.example.seminar_task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seminar_task1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSignupEnd.setOnClickListener {
            signup()
        }

    }

    private fun signup(){
        if(binding.etSignupId.text.toString().isNotEmpty() && binding.etSignupName.text.toString().isNotEmpty() && binding.etSignupPw.text.toString().isNotEmpty()){
            Toast.makeText(this,"회원가입성공", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this,"입력되지 않은 정보가 있습니다",Toast.LENGTH_SHORT).show()
        }
    }

    override fun finish() {
        super.finish()
    }

}