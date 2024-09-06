package com.tps.challenge

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tps.challenge.dao.StoresListDao
import com.tps.challenge.database.AppDatabase
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import repository.StoreRepository
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RepositoryAndDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: StoreRepository
    private lateinit var mockService: TPSCoroutineService
    private lateinit var storeListDao: StoresListDao
    private lateinit var db: AppDatabase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockService = mockk()
        val context = ApplicationProvider.getApplicationContext<Context>()
        repository = StoreRepository(context, mockService) // Add context
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        storeListDao = db.storesListDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeStoresListAndReadInList() { // why this has to be suspend but not for vm network call

        val storesWriteList = listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )

        coEvery { storeListDao.insertAll(storesWriteList) }

        repository.insertAllStoresInDB(storesWriteList)

        coEvery { storeListDao.getAllStores() } returns storesWriteList

         val storesReadList = repository.getAllStoresFromDB()

        assertThat(storesWriteList, equalTo(storesReadList))


        // https://developer.android.com/training/data-storage/room/testing-db
//        val data = listOf<StoreResponse>()
//
//
//        storeListDao.insertAll(storesWriteList)
//        val storesReadList = storeListDao.getAllStores()
//        assertThat(storesWriteList, equalTo(storesReadList))
    }


//    fun writeUserAndReadInList() {
//            val user: User = TestUtil.createUser(3).apply {
//                setName("george")
//            }
//            userDao.insert(user)
//            val byName = userDao.findUsersByName("george")
//            assertThat(byName.get(0), equalTo(user))

//
//
//
//    @RunWith(AndroidJUnit4::class)
//    class SimpleEntityReadWriteTest {
//        private lateinit var userDao: UserDao
//        private lateinit var db: TestDatabase
//
//        @Before
//        fun createDb() {
//            val context = ApplicationProvider.getApplicationContext<Context>()
//            db = Room.inMemoryDatabaseBuilder(
//                context, TestDatabase::class.java).build()
//            userDao = db.getUserDao()
//        }
//
//        @After
//        @Throws(IOException::class)
//        fun closeDb() {
//            db.close()
//        }
//
//        @Test
//        @Throws(Exception::class)
//        fun writeUserAndReadInList() {
//            val user: User = TestUtil.createUser(3).apply {
//                setName("george")
//            }
//            userDao.insert(user)
//            val byName = userDao.findUsersByName("george")
//            assertThat(byName.get(0), equalTo(user))
//        }


    /*
    @Test
    fun testFetchStoresData_Success() = runTest {
        // Mock the service to return a list of stores
        coEvery { mockService.getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE) } returns listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )

        // Trigger the fetch
        repository.fetchStoresList()

        val observedData = withTimeout(2000) { // Wait up to 2 seconds for LiveData update
            val data = mutableListOf<StoreResponse>()
            val observer =
                Observer<List<StoreResponse>> { value -> data.addAll(value) }
            viewModel.storesData.observeForever(observer)
            data
        }
        val firstStore = observedData[0]
        val secondStore = observedData[1]

        Assert.assertEquals(observedData.size, 2)
        Assert.assertEquals(firstStore.name, "Store 1")
        Assert.assertEquals(secondStore.name, "Store 2")
    }

    @Test
    fun testFetchStoresData_Failure() = runTest {
        // Mock the service to return exception
        coEvery { mockService.getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE) } throws Exception("Network Error")

        // Trigger the fetch
        repository.fetchStoresList()

        // Observe LiveData with timeout
        val observedData = withTimeout(1000) { // Wait up to 1 seconds for LiveData update
            val data = mutableListOf<StoreResponse>()
            val observer =
                Observer<List<StoreResponse>> { value -> data.addAll(value) }
            viewModel.storesData.observeForever(observer)
            data
        }

        Assert.assertEquals(observedData.size, 0)
    }

     */
}