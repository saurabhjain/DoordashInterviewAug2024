package com.tps.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.Constants
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreFeedViewModel @Inject constructor(
    private val service: TPSCoroutineService
) : ViewModel() {

    private val _storesData: MutableLiveData<List<StoreResponse>> = MutableLiveData<List<StoreResponse>>()
    val storesData: LiveData<List<StoreResponse>>
        get() = _storesData

    fun fetchStoresData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _storesData.postValue(service.getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE))
            } catch (e: Exception) { // This will catch any exceptions: JSONException/NetworkException/Exception
                e.printStackTrace()
                _storesData.postValue(listOf())  // For the sake of simplicity I am setting data set size = 0 since an exception has occurred
            }
        }
    }

}