package com.tps.challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tps.challenge.dao.StoresListDao
import com.tps.challenge.network.model.StoreResponse

const val DATABASE_NAME = "StoresDB"
const val TABLE_STORES_LIST = "stores_list"

@Database(entities = [StoreResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun storesListDao(): StoresListDao

    companion object {

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build()
                    .also { Instance = it }
            }
        }

    }
}