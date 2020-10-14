package com.bharatagri.mobile

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bharatagri.mobile.movie.MovieDetailsViewModel
import com.bharatagri.mobile.service.api.ApiHelper
import com.bharatagri.mobile.service.modal.MovieDetails
import com.bharatagri.mobile.service.repository.LocalRepository
import com.bharatagri.mobile.service.repository.RemoteRepository
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.bharatagri.mobile.service.utility.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var mainRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var dataObserver: Observer<Resource<MovieDetails>>

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel =
            MovieDetailsViewModel(context, mainRepository, localRepository, networkHelper).apply {
                movieDetailsMutableLiveData.observeForever(dataObserver)
            }

    }

    @Test
    fun givenServerResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1011L
            doReturn(getMovieDummyData())
                .`when`(apiHelper)
                .getMovieDetails(movieId)
            verify(apiHelper).getMovieDetails(movieId)
            verify(dataObserver).onChanged(Resource.success(getMovieDummyData()))
            viewModel.movieDetailsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1011L
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getMovieDetails(movieId)
            viewModel.movieDetailsMutableLiveData.observeForever(dataObserver)
            verify(apiHelper).getMovieDetails(movieId)
            verify(dataObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.movieDetailsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

    private fun getMovieDummyData(): MovieDetails = MovieDetails(
        false,
        "backdrop",
        1,
        arrayListOf(),
        "homepage",
        12,
        "imdbId",
        "English",
        "Title",
        "overview",
        3.32,
        "posterPath",
        "Date",
        3121,
        2,
        arrayListOf(),
        "status",
        "tagline",
        "title",
        false,
        21.32,
        21
    )
}