package com.example.seminar_task1

import com.example.seminar_task1.model.RequestSignIn
import com.example.seminar_task1.model.RequestSignUp
import com.example.seminar_task1.model.ResponseSignIn
import com.example.seminar_task1.model.ResponseSignUp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("auth/signin")
    fun postLogin(
        @Body body: RequestSignIn
    ) : Call<ResponseSignIn>

    @POST("auth/signup")
    fun postSignUp(
        @Body body: RequestSignUp
    ) : Call<ResponseSignUp>

}