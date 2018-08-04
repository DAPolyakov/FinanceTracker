package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.*
import io.alekseimartoyas.financetracker.data.local.converters.FinanceTransactionStateConverter
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.reactivex.Flowable


@Dao
@TypeConverters(FinanceTransactionStateConverter::class)
interface FinanceTransactionDao {

    @Query("SELECT * FROM financetransaction")
    fun getAll(): Flowable<List<FinanceTransaction>>

    @Query("SELECT * FROM financetransaction WHERE state = :doneState")
    fun getAllDone(doneState: FinanceTransactionState = FinanceTransactionState.Done)
            : Flowable<List<FinanceTransaction>>

    @Query("SELECT * FROM financetransaction WHERE state = :scheduledState")
    fun getAllScheduled(scheduledState: FinanceTransactionState = FinanceTransactionState.Waiting)
            : Flowable<List<FinanceTransaction>>

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
    fun getNewTransactionsFromScheduled(now: Long, scheduledWaitingStatus: FinanceTransactionState = FinanceTransactionState.Waiting)
            : Flowable<List<FinanceTransaction>>

}