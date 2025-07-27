package com.ott.ui.movie.list

import app.cash.turbine.test
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.data.movie.data.remote.model.TvShow
import com.ott.data.movie.usecase.GetMoviesUseCase
import com.ott.ui.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var getMoviesUseCase: GetMoviesUseCase


    val page = 1
    private val testDispatcher = StandardTestDispatcher()
    private val mockResponse = MoviesResponse(
        page = 1,
        tvShows = listOf(
            TvShow(
                id = 1,
                name = "Test Movie",
                description = "A test movie description",
                rating = "4.5",
                ratingCount = "1234",
                status = "Released",
                pictures = listOf("image1.jpg", "image2.jpg"),
                country = "IN",
                network = "Test Network",
                startDate = "2023-01-01",
                endDate = "2023-12-31",
                imagePath = "test_image.jpg",
                imageThumbnailPath = "test_image_thumbnail.jpg",
                genres = listOf("Drama", "Action"),
                episodes = listOf(
                    com.ott.data.movie.data.remote.model.Episode(
                        airDate = "2023-01-01", episode = 1, name = "Pilot", season = 1
                    )
                )
            )
        ),
        pages = 2,
        total = "20"
    )

    @Before()
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMoviesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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