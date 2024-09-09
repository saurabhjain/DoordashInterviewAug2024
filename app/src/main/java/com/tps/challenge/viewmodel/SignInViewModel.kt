package com.tps.challenge.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.Constants
import com.tps.challenge.Constants.SHARED_PREF_KEY_TOKEN
import com.tps.challenge.network.TPSCoroutineService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val service: TPSCoroutineService,
    private val applicationContext: Context): ViewModel() {

        private lateinit var sharedPrefs: SharedPreferences

        init {
            createSharedPrefs()
            isUserSignedIn()
        }

    fun isUserSignedIn(): Boolean {
        try {
            val token = sharedPrefs.getString(Constants.SHARED_PREF_KEY_TOKEN, null)
            return !token.isNullOrEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun createSharedPrefs() {
        try {
            sharedPrefs = applicationContext.getSharedPreferences(Constants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTokenAndSigIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveUserToken(service.getUserToken(username, password).token)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveUserToken(token: String) {
        viewModelScope.launch {
            try {
                sharedPrefs.edit().putString(SHARED_PREF_KEY_TOKEN, token).commit()
            } catch (e: Exception) {
              e.printStackTrace()
            }
        }
    }


}