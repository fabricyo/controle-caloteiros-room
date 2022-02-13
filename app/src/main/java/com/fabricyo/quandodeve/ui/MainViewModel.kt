package com.fabricyo.quandodeve.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.data.DebtRepository
import java.lang.IllegalArgumentException

class MainViewModel(private val debtRepository: DebtRepository) : ViewModel() {



    fun insert(debt: Debt){
        debtRepository.insert(debt)
    }

    fun getAll(): LiveData<List<Debt>>{
        return debtRepository.getAll()
    }

    fun update(debt: Debt){
        debtRepository.update(debt)
    }

    fun deleteById(id: Int){
        debtRepository.delete(id)
    }
}

class MainViewModelFactory(private val repository: DebtRepository) :
        ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel Class")
    }
}