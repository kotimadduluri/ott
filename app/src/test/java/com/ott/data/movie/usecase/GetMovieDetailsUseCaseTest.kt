package com.ott.data.movie.usecase

import android.accounts.NetworkErrorException
import com.ott.CoroutineTestRule
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.repository.MovieRepository
import com.ott.support.Fixtures
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetMovieDetailsUseCaseTest {


    @get:Rule
    val rule: TestRule = CoroutineTestRule()
    private val repository: MovieRepository = mockk()
    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        useCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return movie details from repository`() = runTest {
        val movieId = Fixtures.MOVIE_ID
        val expectedResource = Resource.Success(Fixtures.getMovieDetailsResponse())
        coEvery { repository.getMoviesDetails(movieId) } returns expectedResource

        val result = useCase.invoke(movieId)

        assertTrue(result is Resource.Success)
        assertEquals(expectedResource, result)
        assertEquals(expectedResource.data?.tvShow?.name, (result as Resource.Success).data?.tvShow?.name)
    }

    @Test()
    fun `invoke should return error from repository`() = runTest {
        val movieId = Fixtures.MOVIE_ID
        val expectedError = Resource.Error(UiText.PlainString("Error fetching movie details"))
        coEvery { repository.getMoviesDetails(movieId) } returns  expectedError
        val response = useCase.invoke(movieId)

        assertTrue(response is Resource.Error)
        assertEquals(expectedError, response)
        assertEquals(expectedError.message, (response as Resource.Error).message)
    }

    @Test(expected = NetworkErrorException::class)
    fun `invoke should return exception from repository`() = runTest {
        val movieId = Fixtures.MOVIE_ID
        coEvery { repository.getMoviesDetails(movieId) } throws NetworkErrorException("Network Error")
        useCase.invoke(movieId)
    }

}