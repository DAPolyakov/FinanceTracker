package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.*


@Dao
interface FinanceTransactionDao {

    @Query("SELECT * FROM financetransaction")
    fun getAll(): List<FinanceTransaction>

    @Query("SELECT * FROM financetransaction WHERE id = :id")
    fun getById(id: Long): FinanceTransaction

    @Query("SELECT * FROM financetransaction WHERE accountId = :id")
    fun getByAccountId(id: Long): List<FinanceTransaction>

    @Insert
    fun insert(financeTransaction: FinanceTransaction)

    @Update
    fun update(financeTransaction: FinanceTransaction)

    @Delete
    fun delete(financeTransaction: FinanceTransaction)

}