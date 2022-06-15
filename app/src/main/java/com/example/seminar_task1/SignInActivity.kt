package com.example.seminar_task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.example.seminar_task1.dao.MIGRATION_3_4
import com.example.seminar_task1.dao.SignInDatabase
import com.example.seminar_task1.databinding.ActivitySignInBinding
import com.example.seminar_task1.model.LoginData
import com.example.seminar_task1.model.RequestSignIn
import com.example.seminar_task1.model.ResponseSignIn
import com.example.seminar_task1.util.enqueueUtil
import kotlinx.coroutines.*
import retrofit2.Call


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var getSignUpActivityResult: ActivityResultLauncher<Intent>
    private lateinit var db: SignInDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        db = SignInDatabase.getInstance(applicationContext)!!

        //SOPTSharedPreferences.init(this)
        initLogin()
        initClickEvent()
        isAutoLogin()


        //로그인 버튼 클릭시
        signIn()

        //회원가입 버튼 클릭시
        signUp()

        getSignUpActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            //회원가입에서 돌아올 때 결과 값 받아옴
            if (result.resultCode == RESULT_OK) {
                val signupId = result.data?.getStringExtra("id")
                val signupPw = result.data?.getStringExtra("pw")
                binding.etId.setText(signupId)
                binding.etPw.setText(signupPw)
            }
        }
        setContentView(binding.root)
    }

    //자동로그인 db INSERT
    private fun initLogin() {

        CoroutineScope(Dispatchers.IO).launch {
            val isAuto =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    db.signInDao().findIsLogin("UserLogin")
                }

            if(isAuto===null || !isAuto.isAutoLogin){
                db.signInDao().insert(LoginData("UserLogin", false))
            }
        }

    }

    //클릭됐는지 전달
    private fun initClickEvent() {
        binding.btnCheckbox.setOnClickListener {
            binding.btnCheckbox.isSelected = !binding.btnCheckbox.isSelected
            CoroutineScope(Dispatchers.IO).launch {
                db.signInDao().update("UserLogin", binding.btnCheckbox.isSelected)
            }
            //SOPTSharedPreferences.setAutoLogin(binding.btnCheckbox.isSelected)
        }
    }

    //자동로그인
    private fun isAutoLogin() {
        CoroutineScope(Dispatchers.Main).launch {
            val isAuto =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    db.signInDao().findIsLogin("UserLogin")
                }
            if (isAuto.isAutoLogin) {
                Toast.makeText(applicationContext, "자동로그인 되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                finish()
            }

        }

        /*if(SOPTSharedPreferences.getAutoLogin()){
            Toast.makeText(this,"자동로그인 되었습니다",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }*/

    }

    private fun signIn() {
        binding.btnLogin.setOnClickListener {
            if (binding.etId.text.toString().isNotBlank() && binding.etPw.text.toString()
                    .isNotBlank()
            ) {//값이 있는 경우
                val requestSignIn = RequestSignIn(
                    id = binding.etId.text.toString(),
                    password = binding.etPw.text.toString()
                )
                val call: Call<ResponseSignIn> = ServiceCreator.soptService.postLogin(requestSignIn)

                call.enqueueUtil(
                    onSuccess = {
                        Toast.makeText(
                            this@SignInActivity,
                            "${it.data.email}님 반갑습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
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
            } else {
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }


        }
    }


    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            getSignUpActivityResult.launch(intent) //startActivity 대신 사용해서 값 받아올 수 있도록 함
        }

    }

}