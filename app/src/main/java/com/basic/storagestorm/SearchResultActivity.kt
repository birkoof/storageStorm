package com.basic.storagestorm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.DataObject
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class SearchResultActivity : AppCompatActivity() {

    private lateinit var displayString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val searchWord = intent.getStringExtra(Constants.INTENT_EXTRA_SEARCH_WORD)
        searchWord.let {
            displayString = "Search results for $it"
            tvResultFor.text = displayString
            executeSearch(it)
        }
    }

    private fun executeSearch(searchWord: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this@SearchResultActivity)) {
            tvResultFor.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                executeSearch(searchWord)
            }
            progressBar.visibility = View.GONE
            return
        }
        btnRetry.visibility = View.GONE
        doAsync {
            val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
            var list: MutableList<String>?
            try {
                // TODO replace with search method
                list = ikarusApi.getCollBySid("s-000003")?.toMutableList()
            } catch (exception: IOException) {
                Toast.makeText(this@SearchResultActivity, "An error occurred", Toast.LENGTH_LONG).show()
                return@doAsync
            }
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

                recyclerResult.adapter = DatabaseContentAdapter(resultData, this@SearchResultActivity)
            }
        }
    }
}
