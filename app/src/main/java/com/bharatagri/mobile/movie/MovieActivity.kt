package com.bharatagri.mobile.movie

import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_movie
}