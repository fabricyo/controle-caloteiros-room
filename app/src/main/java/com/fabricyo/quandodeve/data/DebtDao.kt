package com.fabricyo.quandodeve.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DebtDao {
    @Query("SELECT * FROM Debt")
    fun getAll(): LiveData<List<Debt>>

    @Query("SELECT * FROM Debt WHERE id = :id")
    fun getDebt(id: Int) : Debt

    @Update
    fun updateDebt(debt: Debt)

    @Query("DELETE FROM debt WHERE id = :id")
    fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(debt: Debt)
}