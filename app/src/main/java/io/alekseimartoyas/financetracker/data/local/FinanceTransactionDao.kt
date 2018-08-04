package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.alekseimartoyas.financetracker.R
import io.reactivex.Flowable


@Dao
interface FinanceTransactionDao {

    @Query("SELECT * FROM financetransaction")
    fun getAll(): Flowable<List<FinanceTransaction>>

    @Query("SELECT * FROM financetransaction WHERE accountId = :id")
    fun getByAccountId(id: Long): Flowable<List<FinanceTransaction>>

    @Insert
    fun insert(financeTransaction: FinanceTransaction)

    @Update
    fun update(financeTransaction: FinanceTransaction)

    @Query("""SELECT *
                    FROM financetransaction
                    WHERE state = :scheduledWaitingStatus
                    AND timeFinish < :now
                    """)
    fun getNewTransactionsFromScheduled(now: Long, scheduledWaitingStatus: Int = R.string.transaction_waiting)
            : Flowable<List<FinanceTransaction>>

}