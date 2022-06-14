package com.example.seminar_task1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_task1.data.model.request.RequestSignIn
import com.example.seminar_task1.data.model.request.RequestSignUp
import com.example.seminar_task1.data.service.ServiceCreator
import com.example.seminar_task1.data.service.SoptService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {
    private val signUpService : SoptService = ServiceCreator.soptService
    var name = MutableLiveData<String>()
    var userId = MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()

    private var _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> get() = _state

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    fun signUp() {
        if (userId.value.toString().isNotBlank() && userPassword.value.toString()
                .isNotBlank() && name.value.toString().isNotEmpty()
        ) {
            val name = name.value.toString()
            val id = userId.value.toString()
            val password = userPassword.value.toString()

            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    signUpService.postSignUp(RequestSignUp(name, id, password))
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        _state.value = true
                        Log.d("signUp", it.toString())
                    }

                }.onFailure {
                    withContext(Dispatchers.Main){
                        _state.value = false
                        Log.d("SignUp", it.toString())
                        when ((it as HttpException).code()) {
                           /* 404 -> Toast.makeText(this, "요청값을 처리할 수 없습니다", Toast.LENGTH_SHORT)
                                .show()
                            404 -> Toast.makeText(this, "존재하는 회원입니다", Toast.LENGTH_SHORT)
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