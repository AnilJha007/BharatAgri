package com.bharatagri.mobile.movie

import android.os.Bundle
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment()
    }

    override fun getLayoutResourceId() = R.layout.activity_movie

    private fun openFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainerView, MoviesFragment()).commit()
    }
}