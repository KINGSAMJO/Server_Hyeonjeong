package com.example.seminar_task1.data.service

import com.example.seminar_task1.data.model.response.ResponseGithubFollowersItem
import com.example.seminar_task1.data.model.response.ResponseGithubRepositoryItem
import com.example.seminar_task1.data.model.response.ResponseGithubUserName
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{userName}")
    suspend fun getUserName(
        @Path("userName") userName : String
    ): ResponseGithubUserName

    @GET("/users/{userId}/followers")
    suspend fun getUserFollowers(
        @Path("userId") userId : String
    ): List<ResponseGithubFollowersItem>

    @GET("/users/{userName}/repos")
    suspend fun getUserRepository(
        @Path("userName") userName : String
    ): List<ResponseGithubRepositoryItem>

}