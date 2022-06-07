package com.example.seminar_task1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seminar_task1.databinding.ActivitySignUpBinding
import com.example.seminar_task1.model.RequestSignUp
import com.example.seminar_task1.model.ResponseSignUp
import com.example.seminar_task1.util.enqueueUtil
import retrofit2.Call


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        signUp()


    }

    //회원가입 완료 버튼 클릭시
    private fun signUp() {
        binding.btnSignupEnd.setOnClickListener {
            if (binding.etSignupId.text.toString()
                    .isNotBlank() && binding.etSignupName.text.toString()
                    .isNotBlank() && binding.etSignupPw.text.toString().isNotEmpty()
            ) {
                val requestSignUp = RequestSignUp(
                    name = binding.etSignupName.text.toString(),
                    id = binding.etSignupId.text.toString(),
                    password = binding.etSignupPw.text.toString()
                )
                val call: Call<ResponseSignUp> =
                    ServiceCreator.soptService.postSignUp(requestSignUp)

                call.enqueueUtil(
                    onSuccess = {
                        Toast.makeText(
                            this@SignUpActivity,
                            "${it?.message}!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        //로그인 페이지로 이동할 때 입력 값을 보내도록 하는 코드
                        val intent =
                            Intent(this@SignUpActivity, SignInActivity::class.java).apply {
                                putExtra("id", requestSignUp.id)
                                putExtra("pw", requestSignUp.password)
                            }
                        setResult(RESULT_OK, intent)
                        if (!isFinishing) {
                            finish()
                        }
                    },
                    onError = {
                        when (it) {
                            404 -> Toast.makeText(this, "요청값을 처리할 수 없습니다", Toast.LENGTH_SHORT)
                                .show()
                            500 -> Toast.makeText(this, "internal server error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
            } else Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()

        }
    }

    override fun finish() {
        super.finish()
    }

}