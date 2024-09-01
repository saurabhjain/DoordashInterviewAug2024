package com.tps.challenge.dagger

import android.app.Application
import android.content.Context
import com.tps.challenge.TCApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val application: TCApplication) {
    @Provides
    @Singleton
    fun getApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}
