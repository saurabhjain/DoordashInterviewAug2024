package com.tps.challenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreDetailViewModel @Inject constructor(
    private val service: TPSCoroutineService
) : ViewModel() {

    private val _storeDetailData: MutableLiveData<StoreDetailsResponse> = MutableLiveData<StoreDetailsResponse>()
    val storeDetailData: LiveData<StoreDetailsResponse>
        get() = _storeDetailData

    fun fetchStoreDetailData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _storeDetailData.postValue(service.getStoreDetails(id))
            } catch (e: Exception) {
                e.printStackTrace()
                _storeDetailData.postValue(null)
            }
        }
    }

}