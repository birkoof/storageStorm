package com.basic.storagestorm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SearchFragment : Fragment(), BackpressHandler {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
