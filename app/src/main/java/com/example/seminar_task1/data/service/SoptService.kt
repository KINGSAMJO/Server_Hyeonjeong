package com.example.seminar_task1.data.service

import com.example.seminar_task1.data.model.request.RequestSignIn
import com.example.seminar_task1.data.model.request.RequestSignUp
import com.example.seminar_task1.data.model.response.BaseResponse
import com.example.seminar_task1.data.model.response.ResponseSignIn
import com.example.seminar_task1.data.model.response.ResponseSignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("auth/signin")
    suspend fun postLogin(
        @Body body: RequestSignIn
    ) : BaseResponse<ResponseSignIn>

    @POST("auth/signup")
    suspend fun postSignUp(
        @Body body: RequestSignUp
    ) : BaseResponse<ResponseSignUp>

}