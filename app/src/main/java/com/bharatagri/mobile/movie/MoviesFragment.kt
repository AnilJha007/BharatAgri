package com.bharatagri.mobile.movie

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseFragment
import com.bharatagri.mobile.movie.MovieDetailsFragment.Companion.MOVIE_ID
import com.bharatagri.mobile.service.modal.Movie
import com.bharatagri.mobile.service.utility.ApiStatus
import com.bharatagri.mobile.utils.Util.getAlertDialog
import com.bharatagri.mobile.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*


@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var spotsDialog: AlertDialog

    override fun setPageTitle() {
        activity?.title = getString(R.string.title_movies)
    }

    override fun getLayoutResourceId() = R.layout.fragment_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initListener()
        initViewModel()
    }

    private fun initListener() {
        tvSortDate.setOnClickListener {
            hideShowSortingOption()
            moviesViewModel.getSortedListBasedOnDate()
        }

        tvSortRating.setOnClickListener {
            hideShowSortingOption()
            moviesViewModel.getSortedListBasedOnRating()
        }

        fabSort.setOnClickListener {
            with(fabSort) {
                when (tag) {
                    getString(R.string.tag_fab_sort) -> {
                        hideShowSortingOption(false)
                    }
                    else -> {
                        hideShowSortingOption()
                    }
                }
            }
        }

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    with((moviesViewModel)) {
                        if (nextPage <= totalPages) {
                            callGetMoviesAPI()
                        }
                    }
                }
            }
        })
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
        moviesAdapter = MoviesAdapter(::handleItemClick)
        rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(false)
            adapter = moviesAdapter
        }
    }

    private fun handleItemClick(movieId: Long) {
        findNavController().navigate(R.id.movieDetailsDest, Bundle().apply {
            putLong(MOVIE_ID, movieId)
        })
    }

    private fun initViewModel() {
        moviesViewModel.moviesMutableLiveData.observe(viewLifecycleOwner, { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized && !moviesViewModel.isFirstTimeLoad) {
                        moviesViewModel.isFirstTimeLoad = true // show loader for first time only
                        spotsDialog.show()
                    }
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
    }

    private fun hideShowSortingOption(shouldHideSortOption: Boolean = true) {
        cardSortItem.visibility = when {
            shouldHideSortOption -> {
                with(fabSort) {
                    setImageResource(R.drawable.sort)
                    tag = getString(R.string.tag_fab_sort)
                }
                View.GONE
            }
            else -> {
                with(fabSort) {
                    setImageResource(R.drawable.close)
                    tag = getString(R.string.tag_fab_close)
                }
                View.VISIBLE
            }
        }
    }

    private fun setData(movies: MutableList<Movie>) {
        fabSort.show()
        moviesAdapter.updateData(movies)
    }

    private fun callGetMoviesAPI() = moviesViewModel.apply { getMovies() }
}