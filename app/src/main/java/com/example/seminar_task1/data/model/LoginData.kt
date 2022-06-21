package com.example.seminar_task1.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LoginData")
data class LoginData(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "isAutoLogin")
    val isAutoLogin: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var baseId: Int = 0
}
