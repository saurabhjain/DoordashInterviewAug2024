package com.tps.challenge.repository

import android.content.Context
import com.tps.challenge.Constants
import com.tps.challenge.database.AppDatabase
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoreRepository @Inject constructor(private val applicationContext: Context,
                                          private val service: TPSCoroutineService) {

    suspend fun fetchStoresList(): List<StoreResponse> {
        return withContext(Dispatchers.IO) {
            try {
                service.getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getAllStoresFromDB(): List<StoreResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                AppDatabase.getDataBase(applicationContext).storesListDao().getAllStores()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun insertAllStoresInDB(employees: List<StoreResponse>) {
        return withContext(Dispatchers.IO) {
            try {
                AppDatabase.getDataBase(applicationContext).storesListDao().insertAll(employees)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}