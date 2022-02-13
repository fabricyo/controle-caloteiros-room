package com.fabricyo.quandodeve

import android.app.Application
import com.fabricyo.quandodeve.data.AppDatabase
import com.fabricyo.quandodeve.data.DebtRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DebtRepository(database.debtDao()) }
}