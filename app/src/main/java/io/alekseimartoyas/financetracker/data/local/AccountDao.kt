package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable


@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll(): Flowable<List<Account>>

    @Insert
    fun insert(account: Account): Long
}