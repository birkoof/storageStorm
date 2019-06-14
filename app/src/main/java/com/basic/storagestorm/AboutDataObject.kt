package com.basic.storagestorm

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.Field
import kotlinx.android.synthetic.main.activity_about_data_object.*
import java.io.IOException

class AboutDataObject : AppCompatActivity() {

    private lateinit var ikarusSearch: IkarusSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_data_object)

        val objectID = intent.getStringExtra(Constants.INTENT_EXTRA_OBJECT_ID)
        objectID.let {
            tvObjectID.text = "Object ID: $it"
            ikarusSearch = IkarusSearch(this)
            ikarusSearch.execute(it)
        }
    }


    class IkarusSearch(private val context: AboutDataObject) : AsyncTask<String, Void, String>() {

        private var objectID: String? = ""

        override fun onPreExecute() {
            super.onPreExecute()
            context.progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            return try {
                objectID = params[0]
                val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
                ikarusApi.get(objectID)
            } catch (exception: IOException) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            context.progressBar.visibility = View.GONE

            if (result != null) {
                val resultData = mutableListOf<Pair<String, Any>>()
                resultData.add(Pair(Constants.CATEGORY, Constants.FIELD))
                resultData.add(Pair(Constants.FIELD, Field(result)))
                context.recyclerResult.adapter = DatabaseContentAdapter(resultData, context)
            } else {
                // TODO handle error
                Toast.makeText(context, "An error ocurred", Toast.LENGTH_LONG).show()
            }
        }
    }
}
