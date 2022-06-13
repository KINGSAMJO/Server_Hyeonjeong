package com.example.seminar_task1.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.seminar_task1.data.dao.SignInDao
import com.example.seminar_task1.data.model.LoginData

@Database(entities = [LoginData::class], version = 5)
abstract class SignInDatabase : RoomDatabase() {
    abstract fun signInDao(): SignInDao

    companion object {
        private var instance: SignInDatabase? = null


        fun getInstance(context: Context): SignInDatabase? {
            if (instance == null) {
                synchronized(SignInDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, SignInDatabase::class.java,
                        "signIn-database" //db가 생성될 떄 사용될 이름
                    )
                        .addMigrations(MIGRATION_3_4)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance


        }
    }

}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {

    }
}