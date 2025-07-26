package com.ott

import android.app.Application
import com.ott.network.di.NetworkModule
import com.ott.di.DI_AppModule
import com.ott.di.DI_MovieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class OttApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Koin or any other dependency injection framework here
         startKoin {
             androidContext(this@OttApp)
             modules(listOf(
                 DI_AppModule,
                 DI_MovieModule,
                 NetworkModule
             ))
         }
    }
}