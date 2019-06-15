package com.basic.storagestorm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.Field
import kotlinx.android.synthetic.main.activity_about_data_object.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class AboutDataObject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_data_object)

        val objectID = intent.getStringExtra(Constants.INTENT_EXTRA_OBJECT_ID)
        objectID.let {
            executeGet(it)
        }
    }

    private fun executeGet(objectID: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this@AboutDataObject)) {
            tvObjectID.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                executeGet(objectID)
            }
            progressBar.visibility = View.GONE
            return
        }
        btnRetry.visibility = View.GONE
        doAsync {
            val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
            var objectJson: String?
            try {
                // TODO replace with search method
                objectJson = ikarusApi.get(objectID)
            } catch (exception: IOException) {
                Toast.makeText(this@AboutDataObject, "An error occurred", Toast.LENGTH_LONG).show()
                return@doAsync
            }
            uiThread {
                progressBar.visibility = View.GONE
                if (objectJson.isNullOrEmpty()) {
                    tvObjectID.text = "No results for $objectID."
                    return@uiThread
                }

                tvObjectID.text = "Object ID: $objectID"
                val resultData = mutableListOf<Pair<String, Any>>()
                resultData.add(Pair(Constants.CATEGORY, Constants.FIELD))
                resultData.add(Pair(Constants.FIELD, Field(objectJson)))
                recyclerResult.adapter = DatabaseContentAdapter(resultData, this@AboutDataObject)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about_data_object, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuDeleteObject -> {
            Toast.makeText(this, "TODO delete object", Toast.LENGTH_LONG).show()
            true
        }
        R.id.menuEditObject -> {
            Toast.makeText(this, "TODO edit object", Toast.LENGTH_LONG).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
