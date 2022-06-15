package com.example.seminar_task1.data.dao

import androidx.room.*
import com.example.seminar_task1.data.model.LoginData

@Dao
interface SignInDao {

    @Insert
    suspend fun insert(isLogin: LoginData)

    @Delete
    suspend fun delete(isLogin: LoginData)

    @Query("DELETE FROM LoginData WHERE id = :user")
    fun deleteIsLogin(user: String)

    @Query("SELECT * FROM LoginData")
    suspend fun getAll(): List<LoginData>

    @Query("SELECT * FROM LoginData WHERE id = :user")
    suspend fun findIsLogin(user: String): LoginData

    @Query("UPDATE LoginData SET isAutoLogin=:isLogin WHERE id = :user")
    suspend fun update(user: String, isLogin: Boolean)

}