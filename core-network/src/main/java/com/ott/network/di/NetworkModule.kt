package com.ott.network.di

import com.ott.network.client.NetworkClient
import com.ott.network.reader.NetworkReader
import com.ott.network.reader.NetworkReaderImp
import org.koin.dsl.module

val NetworkModule = module {
    single<NetworkReader> {
        NetworkReaderImp(get())
    }

    single {
        NetworkClient {
            configuration = get()
        }
    }
}