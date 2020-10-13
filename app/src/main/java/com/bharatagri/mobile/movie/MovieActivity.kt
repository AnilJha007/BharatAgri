package com.bharatagri.mobile.movie

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie.*

@AndroidEntryPoint
class MovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActionBar()
    }

    private fun setUpActionBar() {
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.moviesDest).build()
        setupActionBarWithNavController(
            (navHostFragment as NavHostFragment).navController,
            appBarConfiguration
        )
    }

    override fun getLayoutResourceId() = R.layout.activity_movie

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}