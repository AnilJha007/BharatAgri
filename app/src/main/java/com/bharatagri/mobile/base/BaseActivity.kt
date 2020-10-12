package com.bharatagri.mobile.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
    }

    /* override this method in fragment to attach the layout*/
    abstract fun getLayoutResourceId(): Int
}