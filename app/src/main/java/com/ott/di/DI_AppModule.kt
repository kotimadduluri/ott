package com.ott.di

import com.ott.BuildConfig
import com.ott.network.client.DomainConfiguration
import org.koin.dsl.module

val DI_AppModule = module {
    single {
        DomainConfiguration {
            scheme = "https"
            host = BuildConfig.APP_DOMAIN
            logEnabled = BuildConfig.DEBUG
        }
    }
}