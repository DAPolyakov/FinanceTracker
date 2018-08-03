package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.*


@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getById(id: Long): Account

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

}