package com.example.seminar_task1.model

import com.google.gson.annotations.SerializedName


data class RequestSignIn(
    @SerializedName("email")
    val id:String,
    val password:String,

    /*@PrimaryKey(autoGenerate = true)
    val key : Int*/
)
