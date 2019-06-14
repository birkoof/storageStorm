package com.basic.storagestorm

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


class SearchFragment : Fragment(), BackpressHandler {

    private lateinit var btnSearch: Button
    private lateinit var searchText: TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        btnSearch = view.findViewById(R.id.btnSearch)
        searchText = view.findViewById(R.id.searchText)

        btnSearch.setOnClickListener { onSearchButtonPressed() }
        return view
    }

    override fun onResume() {
        super.onResume()
        // TODO refresh history
    }

    private fun onSearchButtonPressed() {
        if (searchText.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Please enter something to search", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra(Constants.INTENT_EXTRA_SEARCH_WORD, searchText.text.toString())
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
