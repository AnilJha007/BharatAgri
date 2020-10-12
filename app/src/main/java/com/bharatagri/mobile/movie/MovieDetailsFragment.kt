package com.bharatagri.mobile.movie

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseFragment
import com.bharatagri.mobile.service.modal.MovieDetailsResponse
import com.bharatagri.mobile.service.utility.ApiStatus
import com.bharatagri.mobile.utils.Util.getAlertDialog
import com.bharatagri.mobile.utils.Util.getDateFromString
import com.bharatagri.mobile.utils.Util.getDateString
import com.bharatagri.mobile.utils.setImage
import com.bharatagri.mobile.utils.setTextOrHide
import com.bharatagri.mobile.utils.show
import com.bharatagri.mobile.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlin.math.roundToInt


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    companion object {
        const val MOVIE_ID = "movieId"
    }

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var spotsDialog: AlertDialog

    override fun setPageTitle() {
        activity?.apply {
            title = getString(R.string.title_movie_details)
        }
    }

    override fun getLayoutResourceId() = R.layout.fragment_movie_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())

    }

    private fun initViewModel() {
        viewModel.movieDetailsMutableLiveData.observe(requireActivity(), { res ->
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
        callGetMovieDetailsAPI()
    }

    private fun setData(moviesResponse: MovieDetailsResponse) {
        moviesResponse.apply {
            layoutDetails.show()
            ivBackdrop.setImage(backdropPath)

            tvName.text = getString(
                R.string.movie_title_with_date,
                title,
                getDateString(getDateFromString(releaseDate)!!, "yyyy")
            )

            tvOverviewValue.text = overview

            progressScore.progress = (voteAverage * 10).roundToInt()
            tvProgress.text =
                getString(R.string.progress_percentage, (voteAverage * 10).roundToInt().toString())

            val nameList = ArrayList<String>()
            for (i in genres.indices)
                nameList.add(genres[i].name)
            tvGenres.setTextOrHide(TextUtils.join(", ", nameList))

            tvDuration.text = getString(R.string.duration, runtime)

            tvReleaseDate.text = getString(R.string.release_date, releaseDate)

            val languages = ArrayList<String>()
            for (i in spokenLanguages.indices)
                languages.add(spokenLanguages[i].name)
            tvLanguage.setTextOrHide(TextUtils.join(", ", languages))

            tvRevenue.text = getString(R.string.revenue, revenue)

            tvStatus.text = getString(R.string.status, status)
        }
    }

    private fun callGetMovieDetailsAPI() =
        viewModel.getMovieDetails(requireArguments().getLong(MOVIE_ID))
}