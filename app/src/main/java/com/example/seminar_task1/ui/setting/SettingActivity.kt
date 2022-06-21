package com.example.seminar_task1.ui.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seminar_task1.data.database.SignInDatabase
import com.example.seminar_task1.databinding.ActivitySettingBinding
import com.example.seminar_task1.ui.signin.SignInActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var db: SignInDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)

        db = SignInDatabase.getInstance(applicationContext) ?: error("SignInDatebase instanace 오류")
        isAutoLogOut()
        setContentView(binding.root)
    }

    private fun isAutoLogOut() {
        binding.btnRight.setOnClickListener {
            //SOPTSharedPreferences.setAutoLogin(false)
            //SOPTSharedPreferences.setLogout(this)
            CoroutineScope(Dispatchers.IO).launch {
                kotlin.runCatching {
                    db.signInDao().deleteIsLogin("UserLogin")
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SettingActivity, "자동로그인 해제 되었습니다", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@SettingActivity, SignInActivity::class.java))
                        finish()
                    }
                }.onFailure {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SettingActivity, "자동로그인 해제 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}