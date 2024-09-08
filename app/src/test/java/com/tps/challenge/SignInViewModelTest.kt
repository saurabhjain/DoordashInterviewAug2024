package com.tps.challenge

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.tps.challenge.viewmodel.SignInViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var viewModel: SignInViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        MockitoAnnotations.openMocks(this)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        viewModel = SignInViewModel(context)
    }

    @Test
    fun isUserSignedInTrue() {
        `when`(sharedPreferences.getString("username", "")).thenReturn("test_user")
        `when`(sharedPreferences.getString("password", "")).thenReturn("test_password")

        val result = viewModel.isUserSignedIn()

        assert(result)
    }

    @Test
    fun isUserSignedInFalse() {
        `when`(sharedPreferences.getString("username", "")).thenReturn("")
        `when`(sharedPreferences.getString("password", "")).thenReturn("")

        val result = viewModel.isUserSignedIn()

        assert(!result)
    }

    @Test
    fun testSaveUserCredentials() = runTest {
        val username = "test_user"
        val password = "test_password"

        viewModel.saveUserCredentials(username, password)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(editor).putString("username", username)
        verify(editor).putString("password", password)
        verify(editor, times(2)).apply()
    }
}