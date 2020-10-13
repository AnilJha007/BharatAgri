package com.bharatagri.mobile.movie

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.R
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.service.repository.MainRepository
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.bharatagri.mobile.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isFirstTimeLoad = false
    var totalPages = 1 // default value for no. of pages we assume as 1
    var nextPage = 1 // by default we will load first page data

    // live data for movie list
    private val _moviesMutableLiveData = MutableLiveData<Resource<MoviesResponse>>()
    val moviesMutableLiveData: LiveData<Resource<MoviesResponse>>
        get() = _moviesMutableLiveData

    init {
        getMovies(nextPage) // first time call with default page number
    }

    fun getMovies(pageNumber: Int) {
        viewModelScope.launch {
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

    private fun setMoviesData(response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
            _moviesMutableLiveData.postValue(Resource.success(response.body()))
        } else {
            _moviesMutableLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }
}