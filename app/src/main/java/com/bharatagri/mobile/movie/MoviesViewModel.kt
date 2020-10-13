package com.bharatagri.mobile.movie

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.R
import com.bharatagri.mobile.service.modal.Movie
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.service.repository.LocalRepository
import com.bharatagri.mobile.service.repository.RemoteRepository
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.bharatagri.mobile.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isFirstTimeLoad = false
    var totalPages = 1 // default value for no. of pages we assume as 1
    var nextPage = 1 // by default we will load first page data

    // live data for movie list
    private val _moviesMutableLiveData = MediatorLiveData<Resource<MutableList<Movie>>>()
    val moviesMutableLiveData: LiveData<Resource<MutableList<Movie>>>
        get() = _moviesMutableLiveData

    init {
        getMovies() // first time call with default page number
    }

    fun getMovies() {
        viewModelScope.launch {
            _moviesMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    setMoviesData(remoteRepository.getMovies(nextPage))
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
            _moviesMutableLiveData.addSource(localRepository.fetchMovies()) { movies ->
                _moviesMutableLiveData.postValue(Resource.success(movies))
            }
        }
    }

    private fun setMoviesData(response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
            // insert data into database
            viewModelScope.launch {
                response.body()?.let {
                    totalPages = it.totalPages
                    nextPage = it.page + 1
                    localRepository.insertAllMovies(it.results)
                }
            }
        } else {
            _moviesMutableLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }
}