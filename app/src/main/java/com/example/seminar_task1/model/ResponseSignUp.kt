package com.example.seminar_task1.model

data class ResponseSignUp(
    val status : Int,
    val message : String,
    val data : Data
){
    data class Data(
        val id : Int
    )
}
