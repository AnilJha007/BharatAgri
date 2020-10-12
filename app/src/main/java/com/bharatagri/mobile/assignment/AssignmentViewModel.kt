package com.bharatagri.mobile.assignment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bharatagri.mobile.service.modal.MovieDetailsResponse
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.service.repository.MainRepository
import com.bharatagri.mobile.service.utility.NetworkHelper

class AssignmentViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository,
    val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for movie list
    private val _moviesMutableLiveData = MutableLiveData<MoviesResponse>()
    val moviesMutableLiveData: LiveData<MoviesResponse>
        get() = _moviesMutableLiveData

    // live data for movie details
    private val _movieDetailsMutableLiveData = MutableLiveData<MovieDetailsResponse>()
    val movieDetailsMutableLiveData: LiveData<MovieDetailsResponse>
        get() = _movieDetailsMutableLiveData

}