package com.bharatagri.mobile.movie

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseFragment
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.service.utility.ApiStatus
import com.bharatagri.mobile.utils.Util.getAlertDialog
import com.bharatagri.mobile.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var spotsDialog: AlertDialog

    override fun setPageTitle() {
        activity?.title = getString(R.string.title_movies)
    }

    override fun getLayoutResourceId() = R.layout.fragment_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
        moviesAdapter = MoviesAdapter()
        rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(false)
            adapter = moviesAdapter
        }
    }

    private fun initViewModel() {
        viewModel.moviesMutableLiveData.observe(requireActivity(), { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized)
                        spotsDialog.show()
                }
                ApiStatus.SUCCESS -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()

                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()
                    constraintMovies?.snackBar(res.message!!)
                }
            }
        })
        callGetMoviesAPI() // default page number would be 1 for first time
    }

    private fun setData(moviesResponse: MoviesResponse) {
        moviesAdapter.updateData(moviesResponse.results)
    }

    private fun callGetMoviesAPI(pageNumber: Int = 1) = viewModel.getMovies(pageNumber)
}