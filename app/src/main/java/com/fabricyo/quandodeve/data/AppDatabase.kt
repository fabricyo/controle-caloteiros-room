package com.fabricyo.quandodeve.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Debt::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun debtDao() : DebtDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "debt_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}