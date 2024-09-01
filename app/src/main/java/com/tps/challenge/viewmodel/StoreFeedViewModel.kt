package com.tps.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.StoreRepository
import javax.inject.Inject

class StoreFeedViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private val _storesData: MutableLiveData<List<StoreResponse>> = MutableLiveData<List<StoreResponse>>()
    val storesData: LiveData<List<StoreResponse>>
        get() = _storesData

    init {
        getAllStoresFromDB()
    }

    private fun getAllStoresFromDB() {
        viewModelScope.launch {
            try {
                _storesData.value = repository.getAllStoresFromDB()
                if(_storesData.value?.isEmpty() == true) {
                    fetchAndGetStoresData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _storesData.value = listOf()
            }
        }
    }

    fun fetchAndGetStoresData() {
        viewModelScope.launch {
            try {
                // sort by name
                val storesList = repository.fetchStoresList()

                if(storesList.isEmpty())
                {
                    // don't insert in db
                    _storesData.value = listOf()
                }
                else {
                    // insert into db
                    repository.insertAllStoresInDB(storesList)
                    //  _storesData.value = employeeList
                    // Read from db after the insert
                    getAllStoresFromDB()
                }
            } catch (e: Exception) { // This will catch all exceptions
                e.printStackTrace()
                _storesData.value = listOf() // For the sake of simplicity I am only updating progressbar/emptyText state on UI as data set size = 0 since an exception has occurred
                getAllStoresFromDB() // Fetch from db for the last fetch
            }
        }
    }

}