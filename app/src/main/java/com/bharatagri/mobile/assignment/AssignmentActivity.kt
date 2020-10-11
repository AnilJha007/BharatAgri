package com.bharatagri.mobile.assignment

import android.os.Bundle
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignmentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPageTitle()
        openFragment()
    }

    override fun getLayoutResourceId() = R.layout.activity_assignment

    override fun setPageTitle() {
        supportActionBar?.title = getString(R.string.title_movies)
    }

    private fun openFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainerView, AssignmentFragment())
    }
}