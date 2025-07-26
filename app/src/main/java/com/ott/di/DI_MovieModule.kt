package com.ott.di

import com.ott.model.movie.MovieApi
import com.ott.model.movie.usecase.GetMovieDetailsUseCase
import com.ott.model.movie.usecase.GetMoviesUseCase
import com.ott.model.movie.repository.MovieRepository
import com.ott.model.movie.repository.MovieRepositoryImp
import com.ott.network.client.NetworkClient
import com.ott.ui.viewmodel.NavigationViewModel
import com.ott.ui.viewmodel.movie.MovieDetailsScreenViewModel
import com.ott.ui.viewmodel.movie.MoviesScreenViewModel
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
    viewModelOf(::MoviesScreenViewModel)
    viewModelOf(::MovieDetailsScreenViewModel)
    viewModelOf(::NavigationViewModel)
}