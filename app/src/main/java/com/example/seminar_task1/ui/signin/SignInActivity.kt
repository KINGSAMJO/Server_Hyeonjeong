package com.example.seminar_task1.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.seminar_task1.R
import com.example.seminar_task1.data.database.SignInDatabase
import com.example.seminar_task1.data.model.LoginData
import com.example.seminar_task1.data.model.request.RequestSignIn
import com.example.seminar_task1.data.model.response.ResponseSignIn
import com.example.seminar_task1.data.service.ServiceCreator
import com.example.seminar_task1.databinding.ActivitySignInBinding
import com.example.seminar_task1.ui.HomeActivity
import com.example.seminar_task1.ui.base.BaseActivity
import com.example.seminar_task1.ui.signup.SignUpActivity
import com.example.seminar_task1.ui.viewmodel.SignInViewModel
import com.example.seminar_task1.util.enqueueUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call


class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var getSignUpActivityResult: ActivityResultLauncher<Intent>
    private lateinit var db: SignInDatabase
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = SignInDatabase.getInstance(applicationContext)!!
        binding.signIn = signInViewModel
        //SOPTSharedPreferences.init(this)
        initLogin()
        initClickEvent()
        isAutoLogin()
        initObserver()

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
    }

    private fun initObserver() {
        signInViewModel.state.observe(this) {
            when (it) {
                true ->{
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    Toast.makeText(this@SignInActivity, "Hi 반갑습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                false ->{
                    Toast.makeText(this@SignInActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

            }
        }

        signInViewModel.isEmpty.observe(this){
            if(it){
                Toast.makeText(this@SignInActivity,  "아이디 및 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //자동로그인 db INSERT
    private fun initLogin() {

        CoroutineScope(Dispatchers.IO).launch {
            val isAuto =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    db.signInDao().findIsLogin("UserLogin")
                }

            if (isAuto === null || !isAuto.isAutoLogin) {
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
            signInViewModel.signIn()
        }
    }


    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            getSignUpActivityResult.launch(intent) //startActivity 대신 사용해서 값 받아올 수 있도록 함
        }

    }

}