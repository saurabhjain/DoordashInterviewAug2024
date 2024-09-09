package com.tps.challenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tps.challenge.dao.StoresListDao
import com.tps.challenge.database.AppDatabase
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var storesListDao: StoresListDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        storesListDao = db.storesListDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeStoresAndReadInList() = runTest {
        val storesWriteList = listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )
        storesListDao.insertAll(storesWriteList)
        val storesReadList = storesListDao.getAllStores()

        assertThat(storesWriteList, equalTo(storesReadList))
    }
}