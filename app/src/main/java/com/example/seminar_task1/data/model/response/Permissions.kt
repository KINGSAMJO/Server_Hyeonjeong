package com.example.seminar_task1.data.model.response

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)