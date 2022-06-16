package com.example.seminar_task1.data.service

import com.example.seminar_task1.data.model.response.ResponseGithub
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{userId}")
    suspend fun getUserId(
        @Path("userId") userId : String
    ): ResponseGithub

    @GET("/users/{userId}/followers")
    suspend fun getUserFollowers(
        @Path("userId") userId : String
    ): ResponseGithub
    
}