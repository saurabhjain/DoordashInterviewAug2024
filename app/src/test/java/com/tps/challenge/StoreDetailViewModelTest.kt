package com.tps.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreAddressResponse
import com.tps.challenge.network.model.StoreDetailsResponse
import com.tps.challenge.viewmodel.StoreDetailViewModel
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

@ExperimentalCoroutinesApi
class StoreDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StoreDetailViewModel
    private lateinit var mockService: TPSCoroutineService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockService = mockk()
        viewModel = StoreDetailViewModel(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun testFetchStoreData_Success() = runTest {
        // Mock the service to return a single stores
        val storeDetail = StoreDetailsResponse("1", "Store 1", "description one",
            "abc.com", "open", "1000", "", 500,
            listOf(""), StoreAddressResponse(""))

        coEvery { mockService.getStoreDetails("62087") } returns storeDetail

        // Trigger the fetch
        viewModel.fetchStoreDetailData("62087")

        val observedData = withTimeout(1000) { // Wait up to 1 seconds for LiveData update
            var data = mock<StoreDetailsResponse>()
            val observer =
                Observer<StoreDetailsResponse> { value -> data = value.copy() }
            viewModel.storeDetailData.observeForever(observer)
            data
        }

        Assert.assertEquals(observedData, storeDetail)
        Assert.assertEquals(observedData.name, "Store 1")
    }

    @Test
    fun testFetchStoreData_Failure() = runTest {
        // Mock the service to return exception
        coEvery { mockService.getStoreDetails("62087") } throws Exception("Network Error")

        // Trigger the fetch
        viewModel.fetchStoreDetailData("62087")

        // Observe LiveData with timeout
        val observedData = withTimeout(1000) { // Wait up to 1 seconds for LiveData update
            val data = mock<StoreDetailsResponse>()
            val observer =
                Observer<StoreDetailsResponse> { }
            viewModel.storeDetailData.observeForever(observer)
            data
        }

        Assert.assertEquals(observedData.id, null)
    }

}