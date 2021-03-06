package com.basic.storagestorm.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.basic.storagestorm.R
import com.basic.storagestorm.activities.MainActivity
import com.basic.storagestorm.activities.SearchResultActivity
import com.basic.storagestorm.adapters.SearchHistoryAdapter
import com.basic.storagestorm.interfaces.BackpressHandler
import com.basic.storagestorm.models.HistoryEntry
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.DatabaseHandler


class SearchFragment : Fragment(), BackpressHandler {

    private lateinit var btnSearch: Button
    private lateinit var recyclerHistory: RecyclerView
    private lateinit var searchText: TextInputEditText
    private lateinit var tvHistory: TextView
    private lateinit var tvClearHistory: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        btnSearch = view.findViewById(R.id.btnSearch)
        searchText = view.findViewById(R.id.searchText)
        tvHistory = view.findViewById(R.id.tvHistory)
        tvClearHistory = view.findViewById(R.id.tvClearHistory)
        recyclerHistory = view.findViewById(R.id.recyclerHistory)
        btnSearch.setOnClickListener { onSearchButtonPressed() }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity != null && activity is MainActivity) {
            val db = DatabaseHandler((activity as MainActivity).applicationContext)
            val historyList = db.history
            if (historyList.isEmpty()) {
                tvHistory.visibility = View.INVISIBLE
                tvClearHistory.visibility = View.INVISIBLE
            } else {
                tvHistory.visibility = View.VISIBLE
                tvClearHistory.visibility = View.VISIBLE
                tvClearHistory.setOnClickListener {
                    val database =
                        DatabaseHandler((activity as MainActivity).applicationContext)
                    database.clearHistory()
                    onResume()
                }
            }
            recyclerHistory.adapter = SearchHistoryAdapter(historyList.reversed(), activity) {
                onHistoryEntryPressed(it)
            }
        }
    }

    private fun onSearchButtonPressed() {
        if (searchText.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Please enter something to search", Toast.LENGTH_LONG).show()
        } else {
            val searchTerm = searchText.text.toString()
            val intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra(Constants.INTENT_EXTRA_SEARCH_WORD, searchTerm)
            startActivity(intent)
            Handler().postDelayed({
                updateHistoryList(searchTerm)
            }, 1000)
        }
    }

    private fun onHistoryEntryPressed(entry: String) {
        val intent = Intent(activity, SearchResultActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_SEARCH_WORD, entry)
        startActivity(intent)
        Handler().postDelayed({
            updateHistoryList(entry)
        }, 1000)
    }

    private fun updateHistoryList(entry: String) {
        val db = DatabaseHandler((activity as MainActivity).applicationContext)
        db.addEntryToHistory(HistoryEntry(entry, System.currentTimeMillis()))
        val historyList = db.history
        recyclerHistory.adapter = SearchHistoryAdapter(historyList.reversed(), activity) {
            onHistoryEntryPressed(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(activity, "Syncing...", Toast.LENGTH_SHORT).show()
        activity?.sendBroadcast(Intent(Constants.REFRESH_DATA))
        return true
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
