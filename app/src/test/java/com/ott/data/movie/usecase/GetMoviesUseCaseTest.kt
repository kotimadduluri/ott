package com.ott.data.movie.usecase

import android.accounts.NetworkErrorException
import com.ott.CoroutineTestRule
import com.ott.core_ui.util.UiText
import com.ott.data.Resource
import com.ott.data.movie.repository.MovieRepository
import com.ott.support.Fixtures
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetMoviesUseCaseTest {

    @get:Rule
    val rule: TestRule = CoroutineTestRule()
    private val repository: MovieRepository = mockk()
    private lateinit var useCase: GetMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetMoviesUseCase(repository)
    }

    @Test
    fun `invoke should return movie details from repository`() = runTest {
        val page = Fixtures.PAGE
        val expectedResource = Resource.Success(Fixtures.getMoviesResponse())
        coEvery { repository.getMovies(page) } returns expectedResource

        val result = useCase.invoke(page)

        assertTrue(result is Resource.Success)
        assertEquals(expectedResource, result)
        assertEquals(expectedResource.data?.tvShows?.size, (result as Resource.Success).data?.tvShows?.size)
    }

    @Test()
    fun `invoke should return error from repository`() = runTest {
        val page = Fixtures.PAGE
        val expectedError = Resource.Error(UiText.PlainString("Error fetching movies"))
        coEvery { repository.getMovies(page) } returns  expectedError
        val response = useCase.invoke(page)

        assertTrue(response is Resource.Error)
        assertEquals(expectedError, response)
        assertEquals(expectedError.message, (response as Resource.Error).message)
    }

    @Test(expected = NetworkErrorException::class)
    fun `invoke should return exception from repository`() = runTest {
        val page = Fixtures.PAGE
        coEvery { repository.getMovies(page) } throws NetworkErrorException("Network Error")
        useCase.invoke(page)
    }

}