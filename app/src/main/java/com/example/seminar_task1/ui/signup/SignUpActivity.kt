package com.example.seminar_task1.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.seminar_task1.R
import com.example.seminar_task1.data.model.request.RequestSignUp
import com.example.seminar_task1.data.model.response.ResponseSignUp
import com.example.seminar_task1.data.service.ServiceCreator
import com.example.seminar_task1.databinding.ActivitySignUpBinding
import com.example.seminar_task1.ui.base.BaseActivity
import com.example.seminar_task1.ui.signin.SignInActivity
import com.example.seminar_task1.ui.viewmodel.SignUpViewModel
import com.example.seminar_task1.util.enqueueUtil
import retrofit2.Call


class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.signUp = signUpViewModel

        initObserver()
        signUp()


    }

    private fun initObserver(){
        signUpViewModel.state.observe(this){
            when(it){
                true ->{
                    Toast.makeText(
                        this@SignUpActivity,
                        "회원가입 성공",
                        Toast.LENGTH_SHORT
                    ).show()
                    //로그인 페이지로 이동할 때 입력 값을 보내도록 하는 코드
                    val intent =
                        Intent(this@SignUpActivity, SignInActivity::class.java).apply {
                            putExtra("id", binding.etSignupId.text.toString())
                            putExtra("pw", binding.etSignupPw.text.toString())
                        }
                    setResult(RESULT_OK, intent)
                    if (!isFinishing) {
                        finish()
                    }
                }
                false ->{
                    Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signUpViewModel.isEmpty.observe(this){
            if(it){
                Toast.makeText(this@SignUpActivity,"아이디 및 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }


    //회원가입 완료 버튼 클릭시
    private fun signUp() {
        binding.btnSignupEnd.setOnClickListener {
            signUpViewModel.signUp()
        }
    }

    override fun finish() {
        super.finish()
    }

}