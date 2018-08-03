package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable


@Dao
interface FinanceTransactionDao {

    @Query("SELECT * FROM financetransaction")
    fun getAll(): Flowable<List<FinanceTransaction>>

    @Query("SELECT * FROM financetransaction WHERE accountId = :id")
    fun getByAccountId(id: Long): Flowable<List<FinanceTransaction>>

    @Insert
    fun insert(financeTransaction: FinanceTransaction)

}