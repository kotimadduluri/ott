package com.ott.ui.movie.details

import app.cash.turbine.test
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.Episode
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.data.remote.model.TvShow
import com.ott.data.movie.usecase.GetMovieDetailsUseCase
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
class MovieDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private val movieId = 1
    private val mockTvShow = TvShow(
        id = movieId,
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
            Episode(airDate = "2023-01-01", episode = 1, name = "Pilot", season = 1)
        )
    )

    private val mockResponse = MovieDetailsResponse(tvShow = mockTvShow)

    @Before
    fun setUp() {
       Dispatchers.setMain(testDispatcher)
        getMovieDetailsUseCase = mockk()
    }

    @After
    fun tearDown() {
       Dispatchers.resetMain()
    }

    @Test
    fun `emits None then Loading when use case returns data`() = runTest {
        coEvery { getMovieDetailsUseCase(movieId) } returns Resource.Success(mockResponse)
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase)
        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.None)
            assertTrue(awaitItem() is UiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Success when use case returns data`() = runTest {
        coEvery { getMovieDetailsUseCase(movieId) } returns Resource.Success(mockResponse)
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase)
        viewModel.uiState.test {
            awaitItem()
            assertTrue(awaitItem() is UiState.Loading)
            val successState = awaitItem()
            assertTrue(successState is UiState.Success)
            assertEquals(mockTvShow.name, (successState as UiState.Success).data.name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when use case returns error`() = runTest {
        val errorText = UiText.PlainString("Something went wrong")
        coEvery { getMovieDetailsUseCase(movieId) } returns Resource.Error(errorText)
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase)
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
        coEvery { getMovieDetailsUseCase(movieId) } returns Resource.Success(MovieDetailsResponse(tvShow = null))
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase)
        viewModel.uiState.test {
            awaitItem()
            assertTrue(awaitItem() is UiState.Loading)
            assertTrue(awaitItem() is UiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggles favourite state when Favourite intent is triggered`() = runTest {
        coEvery { getMovieDetailsUseCase(movieId) } returns Resource.Success(mockResponse)
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase)
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