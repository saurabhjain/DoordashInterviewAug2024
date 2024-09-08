package com.tps.challenge.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val applicationContext: Context) : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    init {
        createSharedPrefs()
        isUserSignedIn()
    }

    private fun createSharedPrefs() {
        try {
            sharedPreferences = applicationContext.getSharedPreferences("dd_prefs",Context.MODE_PRIVATE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isUserSignedIn(): Boolean {
        try {
            val username = sharedPreferences.getString("username", "")
            val password = sharedPreferences.getString("password", "")
            return !username.isNullOrEmpty() && !password.isNullOrEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun saveUserCredentials(username: String, password: String) {
        viewModelScope.launch {
            try {
                sharedPreferences.edit().putString("username", username).apply()
                sharedPreferences.edit().putString("password", password).apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}