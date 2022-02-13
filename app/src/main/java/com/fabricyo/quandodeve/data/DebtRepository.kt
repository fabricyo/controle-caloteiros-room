package com.fabricyo.quandodeve.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DebtRepository (private val dao: DebtDao) {

    fun insert(debt: Debt) = runBlocking {
        launch ( Dispatchers.IO ){
            dao.insert(debt)
        }
    }

    fun getAll() = dao.getAll()

    fun update(debt: Debt) = runBlocking {
        launch (Dispatchers.Default){
            dao.updateDebt(debt)
        }
    }

    fun delete(id: Int) = runBlocking {
        launch (Dispatchers.Default){
            dao.deleteById(id)
        }
    }

}