package com.tps.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
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

    init {
        fetchStoresData()
    }

    fun fetchStoresData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _storesData.postValue(service.getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE))
            } catch (e: Exception) {
                e.printStackTrace()
                _storesData.postValue(listOf())
            }
        }
    }

    fun updateFavState(id: String, isFav: Boolean): LiveData<List<StoreResponse>> {
        viewModelScope.launch {
            try {
                _storesData.map { it.singleOrNull{ store -> store.id == id}?.fav = isFav }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return storesData
    }

}