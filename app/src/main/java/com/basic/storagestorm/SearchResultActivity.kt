package com.basic.storagestorm

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.DataObject
import kotlinx.android.synthetic.main.activity_search_result.*
import java.io.IOException

class SearchResultActivity : AppCompatActivity() {

    private val ikarusSearch = IkarusSearch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val searchWord = intent.getStringExtra(Constants.INTENT_EXTRA_SEARCH_WORD)
        searchWord.let {
            tvResultFor.text = "Search results for $it"
            ikarusSearch.execute(it)
        }
    }


    inner class IkarusSearch() : AsyncTask<String, Void, MutableList<String>>() {

        private var searchTerm: String? = ""

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): MutableList<String>? {
            return try {
                searchTerm = params[0]
                val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
                // TODO replace with search method
                ikarusApi.getCollBySid("s-000443")?.toMutableList()
            } catch (exception: IOException) {
                null
            }
        }

        override fun onPostExecute(result: MutableList<String>?) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE

            val resultData = mutableListOf<Pair<String, Any>>()
            resultData.add(Pair(Constants.CATEGORY, Constants.DATA_OBJECT))

            if (result == null) {
                tvResultFor.text = "No results for $searchTerm ."
                return
            }

            result.forEach {
                resultData.add(Pair(Constants.DATA_OBJECT, DataObject(it) {
                    Toast.makeText(this@SearchResultActivity, "TODO handle click", Toast.LENGTH_LONG).show()
                }))
            }

            recyclerResult.adapter = DatabaseContentAdapter(resultData, this@SearchResultActivity)
        }
    }
}
