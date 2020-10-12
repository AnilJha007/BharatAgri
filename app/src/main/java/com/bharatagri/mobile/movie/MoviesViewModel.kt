package com.bharatagri.mobile.movie

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.R
import com.bharatagri.mobile.service.modal.MovieDetailsResponse
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.service.repository.MainRepository
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.bharatagri.mobile.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for movie list
    private val _moviesMutableLiveData = MutableLiveData<Resource<MoviesResponse>>()
    val moviesMutableLiveData: LiveData<Resource<MoviesResponse>>
        get() = _moviesMutableLiveData

    // live data for movie details
    private val _movieDetailsMutableLiveData = MutableLiveData<Resource<MovieDetailsResponse>>()
    val movieDetailsMutableLiveData: LiveData<Resource<MovieDetailsResponse>>
        get() = _movieDetailsMutableLiveData

    fun getMovies(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    setMoviesData(mainRepository.getMovies(pageNumber))
                } catch (e: Exception) {
                    _moviesMutableLiveData.postValue(
                        Resource.error(context.getString(R.string.something_went_wrong), null)
                    )
                }
            } else {
                _moviesMutableLiveData.postValue(
                    Resource.error(context.getString(R.string.no_internet_error), null)
                )
            }
        }
    }

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetailsMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    setMovieDetailsData(mainRepository.getMovieDetails(movieId))
                } catch (e: Exception) {
                    _movieDetailsMutableLiveData.postValue(
                        Resource.error(context.getString(R.string.something_went_wrong), null)
                    )
                }
            } else {
                _movieDetailsMutableLiveData.postValue(
                    Resource.error(context.getString(R.string.no_internet_error), null)
                )
            }
        }
    }

    private fun setMoviesData(response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
            _moviesMutableLiveData.postValue(Resource.success(response.body()))
        } else {
            _moviesMutableLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }

    private fun setMovieDetailsData(response: Response<MovieDetailsResponse>) {
        if (response.isSuccessful) {
            _movieDetailsMutableLiveData.postValue(Resource.success(response.body()))
        } else {
            _movieDetailsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}