package com.example.seminar_task1.ui.viewmodel

import android.app.ProgressDialog.show
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_task1.data.model.request.RequestSignIn
import com.example.seminar_task1.data.model.response.ResponseSignIn
import com.example.seminar_task1.data.service.ServiceCreator
import com.example.seminar_task1.data.service.SoptService
import com.example.seminar_task1.ui.HomeActivity
import com.example.seminar_task1.util.enqueueUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException

class SignInViewModel : ViewModel() {
    private val signInService: SoptService = ServiceCreator.soptService
    var userId = MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()

    private var _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> get() = _state

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty : LiveData<Boolean> get() = _isEmpty

    fun signIn() {
        if (userId.value.toString().isNotBlank() && userPassword.toString().isNotBlank()
        ) {//값이 있는 경우

            val id = userId.value.toString()
            val password = userPassword.value.toString()

            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    signInService.postLogin(RequestSignIn(id, password))
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        _state.value = true
                        Log.d("login", it.toString())
                    }

                }.onFailure {
                    withContext(Dispatchers.Main){
                        _state.value = false
                        Log.d("login", it.toString())
                        when ((it as HttpException).code()) {
                            /*404 -> Toast.makeText(this, "요청값을 처리할 수 없습니다", Toast.LENGTH_SHORT)
                                .show()
                            500 -> Toast.makeText(this, "internal server error", Toast.LENGTH_SHORT)
                                .show()*/
                        }
                    }
                }
            }
        } else {
            Log.d("입력", "실패")
            _isEmpty.value = true
        }
    }
}