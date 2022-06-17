package com.example.seminar_task1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_task1.data.model.response.ResponseGithubRepositoryItem
import com.example.seminar_task1.data.service.GithubService
import com.example.seminar_task1.data.service.GithubServiceCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryViewModel : ViewModel() {
    private val githubService: GithubService = GithubServiceCreator.githubService

    private var _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> get() = _state

    private var _repositoryData = MutableLiveData<List<ResponseGithubRepositoryItem>>()
    val repositoryData: LiveData<List<ResponseGithubRepositoryItem>> get() = _repositoryData

    fun repositoryNetwork(userLogin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                githubService.getUserRepository(userLogin)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    Log.d("팔로워 정보", it.toString())
                    _state.value = true
                    _repositoryData.value = it
                }
            }.onFailure {
                Log.d("팔로워 실패", it.toString())
            }
        }

    }
}