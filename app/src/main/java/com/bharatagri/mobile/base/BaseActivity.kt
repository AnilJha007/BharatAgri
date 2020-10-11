package com.bharatagri.mobile.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        setPageTitle()
    }

    abstract fun setPageTitle()

    abstract fun getLayoutResourceId(): Int
}