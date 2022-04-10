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

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var etid : EditText
    private lateinit var etpw : EditText
    private lateinit var getSignUpActivityResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etid = binding.etId
        etpw = binding.etPw

        //로그인 버튼 클릭시
        binding.btnLogin.setOnClickListener {
            if(etid.text.toString().isNotBlank() && etpw.text.toString().isNotBlank()){//값이 있는 경우
                Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"아이디/비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
            }
        }

        //회원가입 버튼 클릭시
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            getSignUpActivityResult.launch(intent) //startActivity 대신 사용해서 값 받아올 수 있도록 함
        }

        //회원가입 후 ID, PW 가져오기
        getSignUpActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            result ->
                //회원가입에서 돌아올 때 결과 값 받아옴
                if(result.resultCode == RESULT_OK){
                    var signupId = result.data?.getStringExtra("id")
                    var signupPw = result.data?.getStringExtra("pw")
                    etid.setText(signupId)
                    etpw.setText(signupPw)
                }
            }
    }

}