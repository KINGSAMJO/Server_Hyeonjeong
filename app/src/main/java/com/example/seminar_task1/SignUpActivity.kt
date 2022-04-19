package com.example.seminar_task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.seminar_task1.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        signUp()


    }

    //회원가입 완료 버튼 클릭시
    private fun signUp(){
        binding.btnSignupEnd.setOnClickListener{
            if(binding.etSignupId.text.toString().isNotBlank() && binding.etSignupName.text.toString().isNotBlank() && binding.etSignupPw.text.toString().isNotEmpty()){
                Toast.makeText(this,"회원가입성공", Toast.LENGTH_SHORT).show()
                var signupId : String = binding.etSignupId.text.toString()
                var signupPw : String = binding.etSignupPw.text.toString()

                //로그인 페이지로 이동할 때 입력 값을 보내도록 하는 코드
                val intent = Intent(this, SignInActivity::class.java).apply{
                    putExtra("id",signupId)
                    putExtra("pw",signupPw)
                }

                setResult(RESULT_OK,intent)
                if (!isFinishing) {
                    finish()
                }
            }else{
                Toast.makeText(this,"입력되지 않은 정보가 있습니다",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun finish() {
        super.finish()
    }

}