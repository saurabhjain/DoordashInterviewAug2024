package com.tps.challenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tps.challenge.database.TABLE_STORES_LIST
import com.tps.challenge.network.model.StoreResponse

@Dao
interface StoresListDao {

    @Query("SELECT * FROM $TABLE_STORES_LIST")
    fun getAllStores(): List<StoreResponse>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(employeesResponseList: List<StoreResponse>?)

}