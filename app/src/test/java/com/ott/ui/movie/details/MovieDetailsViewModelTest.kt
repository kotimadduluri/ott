package com.ott.ui.movie.details

import app.cash.turbine.test
import com.ott.CoroutineTestRule
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.usecase.GetMovieDetailsUseCase
import com.ott.support.Fixtures
import com.ott.ui.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {
    @get:Rule
    val rule: TestRule = CoroutineTestRule(StandardTestDispatcher())

    private lateinit var viewModel: MovieDetailsViewModel
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()
    private val mockResponse = Fixtures.getMovieDetailsResponse()

    private fun createViewModelWithResponse(response: Resource<MovieDetailsResponse>) {
        coEvery { getMovieDetailsUseCase(Fixtures.MOVIE_ID) } returns response
        viewModel = MovieDetailsViewModel(Fixtures.MOVIE_ID, getMovieDetailsUseCase)
    }

    @Test
    fun `emits None then Loading when use case returns data`() = runTest {
        createViewModelWithResponse(Resource.Success(mockResponse))
        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.None)
            assertTrue(awaitItem() is UiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Success when use case returns data`() = runTest {
        createViewModelWithResponse(Resource.Success(mockResponse))
        viewModel.uiState.test {
            awaitItem()
            assertTrue(awaitItem() is UiState.Loading)
            val successState = awaitItem()
            assertTrue(successState is UiState.Success)
            assertEquals(mockResponse.tvShow?.name, (successState as UiState.Success).data.name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when use case returns error`() = runTest {
        val errorText = UiText.PlainString("Something went wrong")
        createViewModelWithResponse(Resource.Error(errorText))
        viewModel.uiState.test {
            awaitItem()
            assertEquals(UiState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is UiState.Error)
            assertEquals(errorText, (errorState as UiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when tvShow is null`() = runTest {
        createViewModelWithResponse(Resource.Success(MovieDetailsResponse(tvShow = null)))
        viewModel.uiState.test {
            awaitItem()
            assertTrue(awaitItem() is UiState.Loading)
            assertTrue(awaitItem() is UiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggles favourite state when Favourite intent is triggered`() = runTest {
        createViewModelWithResponse(Resource.Success(mockResponse))
        viewModel.isFavourite.test {
            assertEquals(false, awaitItem())
            viewModel.onAction(MovieDetailsIntent.Favourite)
            assertEquals(true, awaitItem())
            viewModel.onAction(MovieDetailsIntent.Favourite)
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}