package com.tps.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tps.challenge.network.model.StoreResponse
import com.tps.challenge.viewmodel.StoreFeedViewModel
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
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import repository.StoreRepository

@ExperimentalCoroutinesApi
class StoreFeedViewModelTest {

    /*

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: StoreRepository
    private lateinit var viewModel: StoreFeedViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockRepository = mockk()
        Dispatchers.setMain(testDispatcher)
        viewModel = StoreFeedViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun testGetAllStoresDataFromDB_Success() = runTest {
        // Mock the service to return a list of stores
        coEvery { mockRepository.getAllStoresFromDB() } returns listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )

        // Trigger the get from db
        viewModel.getAllStoresFromDB()

        val observedData = withTimeout(1000) { // Wait up to 1 seconds for LiveData update
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
    fun testFetchAndGetStoresData_Success() = runTest {
        // Mock the service to return a list of stores
        coEvery { mockRepository.fetchStoresList() } returns listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )

        // Trigger the fetch from network and get from db
        viewModel.fetchAndGetStoresData()

        val observedData = withTimeout(1000) { // Wait up to 1 seconds for LiveData update
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

     */

    /*
    @Test
    fun testInsertAllStores_Success() = runTest {

        val storesListToInsert = listOf(
            StoreResponse("1", "Store 1", "description one", "abc.com", "open", "1000"),
            StoreResponse("2", "Store 2", "description two", "xyz.com", "closed", "2000")
        )

        coEvery { mockRepository.insertAllStoresInDB(storesListToInsert) }

        // Trigger the fetch
        mockRepository.fetchStoresData()

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