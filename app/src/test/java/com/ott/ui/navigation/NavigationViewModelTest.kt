package com.ott.ui.navigation

import com.ott.CoroutineTestRule
import com.ott.support.Fixtures
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class NavigationViewModelTest {

    @get:Rule
    val rule: TestRule = CoroutineTestRule(StandardTestDispatcher())

    private lateinit var viewModel: NavigationViewModel

    @Before
    fun setUp() {
        viewModel = NavigationViewModel()
    }

    @Test
    fun `initial backStack contains MovieList`() {
        assertEquals(1, viewModel.backStack.size)
        assertEquals(Screen.MovieList, viewModel.backStack.first())
    }

    @Test
    fun `navigateTo adds screen to backStack`() {
        val navDetails = Screen.MovieDetails(Fixtures.MOVIE_ID)
        viewModel.navigateTo(navDetails)
        assertEquals(2, viewModel.backStack.size)
        assertEquals(navDetails, viewModel.backStack.last())
    }

    @Test
    fun `navigateBack removes last screen from backStack`() {
        val navDetails = Screen.MovieDetails(Fixtures.MOVIE_ID)
        viewModel.navigateTo(navDetails)
        viewModel.navigateBack()
        assertEquals(1, viewModel.backStack.size)
        assertEquals(Screen.MovieList, viewModel.backStack.last())
    }

    @Test
    fun `navigateBack does nothing if only one screen in backStack`() {
        viewModel.navigateBack()
        assertEquals(1, viewModel.backStack.size)
        assertEquals(Screen.MovieList, viewModel.backStack.last())
    }

}