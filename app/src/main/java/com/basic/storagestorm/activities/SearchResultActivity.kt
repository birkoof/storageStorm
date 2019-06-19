package com.basic.storagestorm.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class SearchResultActivity : AppCompatActivity() {

    private lateinit var displayString: String
    private lateinit var searchWord: String
    private lateinit var refreshContentReceiver: RefreshContentReceiver
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        progressBar = findViewById(R.id.progressBar)
        searchWord = intent.getStringExtra(Constants.INTENT_EXTRA_SEARCH_WORD)
        displayString = "Search results for $searchWord"
        tvResultFor.text = displayString
        executeSearch(searchWord)
    }

    override fun onResume() {
        super.onResume()
        refreshContentReceiver = RefreshContentReceiver()
        registerReceiver(refreshContentReceiver, IntentFilter(Constants.REFRESH_DATA))
        executeSearch(searchWord)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(refreshContentReceiver)
    }

    private fun executeSearch(searchWord: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this)) {
            tvResultFor.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            recyclerResult.visibility = View.GONE
            btnRetry.setOnClickListener {
                executeSearch(searchWord)
            }
            progressBar.visibility = View.GONE
            return
        }
        btnRetry.visibility = View.GONE
        doAsync {
            try {
                val list = IkarusApi(Constants.UTILITIES_SERVER_URL).searchObj(searchWord)
                uiThread {
                    progressBar.visibility = View.GONE
                    tvResultFor.text = displayString

                    val resultData = mutableListOf<Pair<String, Any>>()
                    resultData.add(Pair(Constants.CATEGORY, Constants.DATA_OBJECT))

                    if (list.isNullOrEmpty()) {
                        tvResultFor.text = "No results for $searchWord."
                        return@uiThread
                    }

                    list.forEach {
                        resultData.add(Pair(Constants.DATA_OBJECT, DataObject(it) {
                            val intent = Intent(this@SearchResultActivity, AboutDataObject::class.java)
                            intent.putExtra(Constants.INTENT_EXTRA_OBJECT_ID, it)
                            startActivity(intent)
                        }))
                    }

                    recyclerResult.visibility = View.VISIBLE
                    recyclerResult.adapter = DatabaseContentAdapter(resultData, this@SearchResultActivity)
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "An error occurred", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class RefreshContentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            executeSearch(searchWord)
        }
    }

}
