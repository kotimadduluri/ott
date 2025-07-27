package com.ott.di

import com.ott.data.movie.MovieApi
import com.ott.data.movie.usecase.GetMovieDetailsUseCase
import com.ott.data.movie.usecase.GetMoviesUseCase
import com.ott.data.movie.repository.MovieRepository
import com.ott.data.movie.repository.MovieRepositoryImp
import com.ott.network.client.NetworkClient
import com.ott.ui.navigation.NavigationViewModel
import com.ott.ui.movie.details.MovieDetailsViewModel
import com.ott.ui.movie.list.MoviesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val DI_MovieModule = module {

    single<MovieApi> {
        get<NetworkClient>().buildApi(MovieApi::class.java)
    }

    singleOf(::MovieRepositoryImp) bind MovieRepository::class

    singleOf(::GetMoviesUseCase)
    singleOf(::GetMovieDetailsUseCase)
    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::NavigationViewModel)
}