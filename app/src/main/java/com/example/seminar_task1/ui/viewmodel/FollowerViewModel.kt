package com.example.seminar_task1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_task1.data.model.response.ResponseGithubFollowers
import com.example.seminar_task1.data.model.response.ResponseGithubFollowersItem
import com.example.seminar_task1.data.model.response.ResponseGithubUserName
import com.example.seminar_task1.data.service.GithubService
import com.example.seminar_task1.data.service.GithubServiceCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowerViewModel: ViewModel(){
    private val githubService: GithubService = GithubServiceCreator.githubService
    var login = MutableLiveData<String>()
    var name = MutableLiveData<String>()

    private var _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> get() = _state

    private var _followerData = MutableLiveData<List<ResponseGithubFollowersItem>>()
    val followerData : LiveData<List<ResponseGithubFollowersItem>> get() = _followerData

    fun followerNetwork(userLogin : String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                githubService.getUserFollowers(userLogin)
            }.onSuccess {
                withContext(Dispatchers.Main){
                    Log.d("팔로워 정보",it.toString())
                    _state.value = true
                    _followerData.value = it
                }
            }.onFailure {
                Log.d("팔로워 실패",it.toString())
            }
        }

    }
}