package com.bharatagri.mobile.movie

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.R
import com.bharatagri.mobile.service.modal.MovieDetails
import com.bharatagri.mobile.service.repository.LocalRepository
import com.bharatagri.mobile.service.repository.RemoteRepository
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.bharatagri.mobile.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieDetailsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for movie details
    private val _movieDetailsMutableLiveData = MediatorLiveData<Resource<MovieDetails>>()
    val movieDetailsMutableLiveData: LiveData<Resource<MovieDetails>>
        get() = _movieDetailsMutableLiveData

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetailsMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    setMovieDetailsData(remoteRepository.getMovieDetails(movieId))
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
            _movieDetailsMutableLiveData.addSource(localRepository.fetchMovieDetails(movieId)) { movie ->
                _movieDetailsMutableLiveData.postValue(Resource.success(movie))
            }
        }
    }

    private fun setMovieDetailsData(response: Response<MovieDetails>) {
        if (response.isSuccessful) {
            // insert data into database
            viewModelScope.launch {
                response.body()?.let {
                    localRepository.insertMovieDetails(it)
                }
            }
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