package com.bharatagri.mobile.assignment

import android.os.Bundle
import android.view.View
import com.bharatagri.mobile.R
import com.bharatagri.mobile.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignmentFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_assignment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialStateData()
    }

    private fun setInitialStateData() {
        // TODO("Not yet implemented")
    }
}