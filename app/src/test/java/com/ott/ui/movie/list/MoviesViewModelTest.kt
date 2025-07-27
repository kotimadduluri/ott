package com.ott.ui.movie.list

import app.cash.turbine.test
import com.ott.CoroutineTestRule
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.data.movie.usecase.GetMoviesUseCase
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
class MoviesViewModelTest {

    @get:Rule
    val rule: TestRule = CoroutineTestRule(StandardTestDispatcher())
    val page = Fixtures.PAGE

    private val getMoviesUseCase: GetMoviesUseCase = mockk()
    private lateinit var viewModel: MoviesViewModel
    private val mockResponse = Fixtures.getMoviesResponse()

    private fun createViewModelWithResponse(response: Resource<MoviesResponse>) {
        coEvery { getMoviesUseCase(page) } returns response
        viewModel = MoviesViewModel(getMoviesUseCase)
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
            val success = awaitItem()
            assertTrue(success is UiState.Success)
            assertEquals(mockResponse.tvShows?.size, (success as UiState.Success).data.size)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `emits None Loading then Error when use case returns data`() = runTest {
        val error = UiText.PlainString("Error")
        createViewModelWithResponse(Resource.Error(error))

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.None)
            assertTrue(awaitItem() is UiState.Loading)
            val errorState = awaitItem()
            assertTrue(errorState is UiState.Error)
            assertEquals(error, (errorState as UiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }

    }

}