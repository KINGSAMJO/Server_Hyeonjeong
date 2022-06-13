package com.example.seminar_task1.data.model.response

data class ResponseSignIn(
    val status:Int,
    val message :String,
    val data : Data
){
    data class Data(
        val name: String,
        val email: String
    )
}
