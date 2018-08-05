package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.*
import io.alekseimartoyas.financetracker.data.local.converters.BigDecimalConverter
import java.math.BigDecimal


@Dao
@TypeConverters(BigDecimalConverter::class)
interface AccountFinanceTransactionDao {

    @Insert
    fun insertFinanceTransaction(transaction: FinanceTransaction)

    @Update
    fun updateFinanceTransaction(transaction: FinanceTransaction)

    @Query("UPDATE account SET amount = amount + :sum WHERE id = :accountId")
    fun updateAccount(accountId: Long, sum: BigDecimal)

    @Transaction
    fun insertFinanceTransactionUpdateAccountAmount(
            transaction: FinanceTransaction,
            accountId: Long,
            sum: BigDecimal) {

        insertFinanceTransaction(transaction)
        updateAccount(accountId, sum)
    }

    @Transaction
    fun updateFinanceTransactionUpdateAccountAmount(
            transaction: FinanceTransaction,
            accountId: Long,
            sum: BigDecimal) {

        updateFinanceTransaction(transaction)
        updateAccount(accountId, sum)
    }
}