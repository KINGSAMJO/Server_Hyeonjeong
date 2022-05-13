package com.example.seminar_task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.seminar_task1.databinding.ActivitySignInBinding
import com.example.seminar_task1.model.RequestSignIn
import com.example.seminar_task1.model.ResponseSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var etid : EditText
    private lateinit var etpw : EditText
    private lateinit var getSignUpActivityResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
        etid = binding.etId
        etpw = binding.etPw

        //로그인 버튼 클릭시
        signIn()

        //회원가입 버튼 클릭시
        signUp()

        //회원가입 후 ID, PW 가져오기
        signUp()
        getSignUpActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            result ->
                //회원가입에서 돌아올 때 결과 값 받아옴
                if(result.resultCode == RESULT_OK){
                    val signupId = result.data?.getStringExtra("id")
                    val signupPw = result.data?.getStringExtra("pw")
                    etid.setText(signupId)
                    etpw.setText(signupPw)
                }
            }
    }

    private fun signIn(){
        binding.btnLogin.setOnClickListener {
            if(etid.text.toString().isNotBlank() && etpw.text.toString().isNotBlank()){//값이 있는 경우
                Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"아이디/비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            getSignUpActivityResult.launch(intent) //startActivity 대신 사용해서 값 받아올 수 있도록 함
        }

    }

    private fun initEvent(){
        binding.btnLogin.setOnClickListener {
            loginNetwork()
        }
    }

    private fun loginNetwork(){
        val requestSignIn = RequestSignIn(
            id= binding.etId.text.toString(),
            password = binding.etPw.text.toString()
        )
        val call : Call<ResponseSignIn> = ServiceCreator.soptService.postLogin(requestSignIn)

        call.enqueue(object : Callback<ResponseSignIn> {
            override fun onResponse(
                call : Call<ResponseSignIn>,
                response: Response<ResponseSignIn>
            ){
                if(response.isSuccessful){
                    val data = response.body()?.data
                    Toast.makeText(this@SignInActivity, "${data?.email}님 반갑습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))

                }else Toast.makeText(this@SignInActivity,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseSignIn>, t: Throwable) {
                Log.e("NetworkTEst,","error:$t")
            }


        })

    }

}