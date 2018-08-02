package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update


interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll(): List<FinanceTransaction>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getById(id: Long): Account

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

}